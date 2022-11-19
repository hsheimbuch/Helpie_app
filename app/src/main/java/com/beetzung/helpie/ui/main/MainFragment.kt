package com.beetzung.helpie.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.data.model.DaysData
import com.beetzung.helpie.databinding.FragmentMainBinding
import com.beetzung.helpie.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.daysRecords.observe(viewLifecycleOwner) { data ->
            binding.updateView(data)
        }
    }

    private fun FragmentMainBinding.updateView(daysData: DaysData) {
        val builder = StringBuilder()
        for (key in daysData.keys) {
            daysData[key]?.let {
                builder.append("$key: $it\n")
            }
        }
        mainText.text = builder.toString()
    }
}