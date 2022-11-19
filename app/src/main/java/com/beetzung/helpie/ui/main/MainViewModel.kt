package com.beetzung.helpie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.beetzung.helpie.data.Database
import com.beetzung.helpie.data.model.DaysData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: Database
): ViewModel() {
    val daysRecords: LiveData<DaysData> = liveData {
        database.getDaysData()
    }

}