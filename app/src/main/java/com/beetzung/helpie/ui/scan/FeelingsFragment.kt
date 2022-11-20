package com.beetzung.helpie.ui.scan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.core.navController
import com.beetzung.helpie.core.onDisplayed
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.data.model.PreEmotion
import com.beetzung.helpie.data.model.Record
import com.beetzung.helpie.databinding.FragmentFeelingsBinding
import com.beetzung.helpie.ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeelingsFragment : BaseFragment(R.layout.fragment_feelings) {
    private val binding: FragmentFeelingsBinding by viewBinding(FragmentFeelingsBinding::bind)
    private val viewModel: FeelingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupView(FeelingsFragmentArgs.fromBundle(requireArguments()).emotion.toPreEmotion())
        viewModel.recordMade.observe(viewLifecycleOwner) { event ->
            event.handle { record ->
                hideLoadingDialog()
                record?.let(this@FeelingsFragment::navigateToAdvice) ?: showError()
            }
        }
    }

    private fun showError() {
        Snackbar.make(
            binding.root,
            R.string.snackbar_error,
            Snackbar.LENGTH_SHORT
        ).setAction(R.string.button_try_again) {
            binding.feelingsButton.performClick()
        }.show()
    }

    private fun FragmentFeelingsBinding.setupView(emotion: PreEmotion) {
        val emotionAdapter = EmotionAdapter(emotion)
        feelingsCardEmotionRecycler.adapter = emotionAdapter
        feelingsCardEmotionRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        feelingsCardEmotionRecycler.onDisplayed {
            feelingsCardEmotionRecycler.scrollToPosition(emotionAdapter.selectedEmotion.ordinal)
        }
        feelingsButton.setOnClickListener {
            showLoadingDialog()
            viewModel.sendAnswers(emotionAdapter.selectedEmotion, feelingsSeekBar.progress)
        }
    }

    private fun navigateToAdvice(record: Record) {
        val action = FeelingsFragmentDirections.actionNavigationFeelingsToNavigationAdvice(record)
        navController.navigate(action)
    }
}

