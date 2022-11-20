package com.beetzung.helpie.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.core.CalendarHelper
import com.beetzung.helpie.core.TAG
import com.beetzung.helpie.core.navController
import com.beetzung.helpie.data.model.Record
import com.beetzung.helpie.databinding.FragmentMainBinding
import com.beetzung.helpie.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    private val calendarAdapter = CalendarAdapter { record ->
        navigateToAdvice(record)
    }

    private var year: Int = CalendarHelper.getCurrentYear()
    private var month: Int = CalendarHelper.getCurrentMonth()
    set(value) {
        when (value) {
            13 -> {
                field = 1
                year++
            }
            0 -> {
                field = 12
                year--
            }
            else -> {
                field = value
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        binding.setupView()
        viewModel.daysRecords.observe(viewLifecycleOwner) { data ->
            calendarAdapter.setDaysOfMonth(data)
        }
    }

    private fun FragmentMainBinding.setupView() {
        mainButton.setOnClickListener {
            navController.navigate(MainFragmentDirections.actionMainFragmentToCameraFragment())
        }
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(requireContext(), 7)
        mainCalendarRecycler.layoutManager = layoutManager
        mainCalendarRecycler.adapter = calendarAdapter
        mainButtonLeft.setOnClickListener {
            month--
            viewModel.refresh(year, month)
        }
        mainButtonRight.setOnClickListener {
            month++
            viewModel.refresh(year, month)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh(year, month)
    }

    private fun navigateToAdvice(record: Record) {
        navController.navigate(MainFragmentDirections.actionNavigationHomeToNavigationAdvice(record))
    }
}