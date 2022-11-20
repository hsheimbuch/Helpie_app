package com.beetzung.helpie.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.beetzung.helpie.core.CalendarHelper
import com.beetzung.helpie.core.TAG
import com.beetzung.helpie.data.Database
import com.beetzung.helpie.data.model.Record
import com.beetzung.helpie.ui.main.CalendarAdapter.Companion.EmptyDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: Database
) : ViewModel() {
    private val _daysData: MutableLiveData<CalendarData> = MutableLiveData()
    val daysRecords: LiveData<CalendarData> = _daysData

    init {
        refresh(CalendarHelper.getCurrentYear(), CalendarHelper.getCurrentMonth())
    }

    fun refresh(year: Int, month: Int) {
        viewModelScope.launch {
            val result = mutableMapOf<DayInfo, Record?>()
            val daysData = database.getDaysData().also { Log.d(TAG, "daysData: $it") }
            val daysInMonth = CalendarHelper.getDaysInMonthArray(year, month)
            for (day in daysInMonth) {
                val thisDayData = daysData.filter {
                    CalendarHelper.checkIfCurrentMonth(it.key, year, month)
                }.filter {
                    CalendarHelper.getDayFromTimeStamp(it.key) == day?.toInt()
                }.values.firstOrNull()

                val dayInfo = day?.let { DayInfo(CalendarHelper.getCurrentDay() == day.toInt(), day) } ?: EmptyDay

                result[dayInfo] = thisDayData
            }
            _daysData.value = result
        }
    }
}