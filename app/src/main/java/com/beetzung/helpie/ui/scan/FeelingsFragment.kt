package com.beetzung.helpie.ui.scan

import android.graphics.LinearGradient
import android.graphics.Shader.TileMode
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.core.onDisplayed
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.databinding.FragmentFeelingsBinding
import com.beetzung.helpie.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeelingsFragment : BaseFragment(R.layout.fragment_feelings) {
    private val binding: FragmentFeelingsBinding by viewBinding(FragmentFeelingsBinding::bind)
    private val viewModel: FeelingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupView(FeelingsFragmentArgs.fromBundle(requireArguments()).emotion)
    }

    private fun FragmentFeelingsBinding.setupView(emotion: Emotion) {
        val emotionAdapter = EmotionAdapter(emotion)
        feelingsCardEmotionRecycler.adapter = emotionAdapter
        feelingsCardEmotionRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        feelingsCardEmotionRecycler.onDisplayed {
            feelingsCardEmotionRecycler.scrollToPosition(emotionAdapter.selectedEmotion.ordinal)
        }
        feelingsButton.setOnClickListener {
            viewModel.sendAnswers(emotionAdapter.selectedEmotion, feelingsSeekBar.progress)
        }
    }
}

