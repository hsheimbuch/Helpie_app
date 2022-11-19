package com.beetzung.helpie.ui.scan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.databinding.FragmentFeelingsBinding
import com.beetzung.helpie.ui.BaseFragment
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeelingsFragment : BaseFragment(R.layout.fragment_feelings) {
    private val binding: FragmentFeelingsBinding by viewBinding(FragmentFeelingsBinding::bind)
    private val viewModel: FeelingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupView()
    }

    private fun FragmentFeelingsBinding.setupView() {
        val listener = ChipGroup.OnCheckedStateChangeListener { _, _ ->
            if (checkChips()) {
                feelingsButton.isEnabled = true
            }
        }
        feelingsCardHowLongChipGroup.setOnCheckedStateChangeListener(listener)
        feelingsCardEmotionChipGroup.setOnCheckedStateChangeListener(listener)
        feelingsButton.setOnClickListener {

        }
    }

    private fun FragmentFeelingsBinding.checkChips(): Boolean {
        if (feelingsCardHowLongChipGroup.checkedChipId != -1) {
            if (feelingsCardEmotionChipGroup.checkedChipId != -1) {
                return true
            }
        }
        return false
    }
}

