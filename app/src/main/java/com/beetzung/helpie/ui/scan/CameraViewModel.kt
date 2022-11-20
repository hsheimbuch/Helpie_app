package com.beetzung.helpie.ui.scan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beetzung.helpie.core.DataEvent
import com.beetzung.helpie.core.TAG
import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.ApiEmotion
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val recognitionApi: RecognitionApi
) : ViewModel() {
    private val _imageSent = MutableLiveData<DataEvent<ApiEmotion?>>()
    val imageSent: LiveData<DataEvent<ApiEmotion?>> = _imageSent

    private val storage = Firebase.storage

    fun sendImage(fileName: String, byteArray: ByteArray) {
        viewModelScope.launch {
            val url = getImageUrl(fileName, byteArray)
            url?.let {
                uploadImage(it)
            }
        }
    }

    private suspend fun getImageUrl(fileName: String, byteArray: ByteArray): String? = suspendCoroutine { continuation ->
        var storageRef = storage.reference
        var imageRef = storageRef.child("images/$fileName")
        imageRef.putBytes(byteArray).addOnFailureListener {
            continuation.resume(null)
        }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                continuation.resume(uri.toString())
            }
        }
    }

    private suspend fun uploadImage(url: String) {
        recognitionApi.getRecognitionResult(url).onSuccess {
            _imageSent.value = DataEvent(it)
        }.onFailure {
            Log.e(TAG, "sendImage: ", it)
            _imageSent.value = DataEvent(null)
        }
    }
}