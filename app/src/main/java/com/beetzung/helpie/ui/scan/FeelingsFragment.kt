package com.beetzung.helpie.ui.scan

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.databinding.FragmentFeelingsBinding
import com.beetzung.helpie.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeelingsFragment : BaseFragment(R.layout.fragment_feelings) {
    private val binding: FragmentFeelingsBinding by viewBinding(FragmentFeelingsBinding::bind)
    private val viewModel: FeelingsViewModel by viewModels()
}

