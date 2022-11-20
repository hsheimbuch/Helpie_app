package com.beetzung.helpie.ui.scan

import androidx.lifecycle.*
import com.beetzung.helpie.core.DataEvent
import com.beetzung.helpie.data.Database
import com.beetzung.helpie.data.LocationService
import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.data.model.Record
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeelingsViewModel @Inject constructor(
    private val database: Database,
    private val recognitionApi: RecognitionApi,
    private val locationService: LocationService
) : ViewModel() {
    private val _recordMade = MutableLiveData<DataEvent<Record?>>()
    val recordMade: LiveData<DataEvent<Record?>> = _recordMade

    fun sendAnswers(emotion: Emotion, level: Int) {
        viewModelScope.launch {
            val countryCode = locationService.getCountryCode()
            recognitionApi.getCards(emotion, level, countryCode).onSuccess { cards ->
                val record = Record(System.currentTimeMillis(), emotion, 0, cards.toString())
                database.updateRecord(record.timestamp, record)
                _recordMade.value = DataEvent(record)
            }.onFailure {
                _recordMade.value = DataEvent(null)
            }
        }
    }
}