package com.beetzung.helpie.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.core.TAG
import com.beetzung.helpie.core.getDaysInMonthArray
import com.beetzung.helpie.core.navController
import com.beetzung.helpie.data.model.DaysData
import com.beetzung.helpie.databinding.FragmentMainBinding
import com.beetzung.helpie.ui.BaseFragment
import com.beetzung.helpie.views.CalendarAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        binding.setupView()
        viewModel.daysRecords.observe(viewLifecycleOwner) { data ->
            binding.updateView(data)
        }
    }

    private fun FragmentMainBinding.setupView() {
        mainButton.setOnClickListener {
            navController.navigate(MainFragmentDirections.actionMainFragmentToCameraFragment())
        }
        val days = getDaysInMonthArray()
        val calendarAdapter = CalendarAdapter(days) { p, t ->
            //TODO calendar click
            Toast.makeText(requireContext(), "Open day $t", Toast.LENGTH_SHORT).show()
        }
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(requireContext(), 7)
        mainCalendarRecycler.layoutManager = layoutManager
        mainCalendarRecycler.adapter = calendarAdapter
    }

    private fun FragmentMainBinding.updateView(daysData: DaysData) {
        val builder = StringBuilder()
        for (key in daysData.keys) {
            daysData[key]?.let {
                builder.append("$key: $it\n")
            }
        }
        this.apply {} //TODO update
    }
}