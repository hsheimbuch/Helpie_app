package com.beetzung.helpie.ui.scan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beetzung.helpie.data.Database
import com.beetzung.helpie.data.LocationService
import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.Emotion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeelingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val database: Database,
    private val recognitionApi: RecognitionApi,
    private val locationService: LocationService
): ViewModel() {

    private val emotion = FeelingsFragmentArgs.fromSavedStateHandle(savedStateHandle).emotion

    fun sendAnswers(emotion: Emotion) {
        viewModelScope.launch {
            val countryCode = locationService.getCountryCode()
        }
    }
}