package com.beetzung.helpie.ui.scan

import androidx.lifecycle.ViewModel
import com.beetzung.helpie.data.Database
import com.beetzung.helpie.data.RecognitionApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeelingsViewModel @Inject constructor(
    private val database: Database,
    private val recognitionApi: RecognitionApi
): ViewModel() {

    fun sendAnswers() {

    }
}