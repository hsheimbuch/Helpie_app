package com.beetzung.helpie.ui.advice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.data.model.Record
import com.beetzung.helpie.databinding.FragmentAdviceBinding
import com.beetzung.helpie.ui.BaseFragment

class AdviceFragment : BaseFragment(R.layout.fragment_advice) {
    private val binding: FragmentAdviceBinding by viewBinding(FragmentAdviceBinding::bind)
    private val viewModel: AdviceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupView(AdviceFragmentArgs.fromBundle(requireArguments()).record)
    }

    private fun FragmentAdviceBinding.setupView(record: Record) {
        adviceText.text = record.text
    }
}

