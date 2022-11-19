package com.beetzung.helpie.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beetzung.helpie.core.DataEvent
import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.Emotion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val recognitionApi: RecognitionApi
) : ViewModel() {
    private val _imageSent = MutableLiveData<DataEvent<Emotion?>>()
    val imageSent: LiveData<DataEvent<Emotion?>> = _imageSent

    fun sendImage(image: ByteArray) {
        viewModelScope.launch {
            recognitionApi.getRecognitionResult(image).onSuccess {
                _imageSent.value = DataEvent(it)
            }.onFailure {
                _imageSent.value = DataEvent(null)
            }

        }
    }
}