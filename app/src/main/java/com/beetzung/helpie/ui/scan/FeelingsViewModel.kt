package com.beetzung.helpie.ui.scan

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beetzung.helpie.R
import com.beetzung.helpie.core.DataEvent
import com.beetzung.helpie.data.Database
import com.beetzung.helpie.data.LocationService
import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.data.model.PreEmotion
import com.beetzung.helpie.data.model.Record
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeelingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: Database,
    private val recognitionApi: RecognitionApi,
    private val locationService: LocationService
) : ViewModel() {
    private val _recordMade = MutableLiveData<DataEvent<Record?>>()
    val recordMade: LiveData<DataEvent<Record?>> = _recordMade

    fun sendAnswers(emotion: PreEmotion, level: Int) {
        viewModelScope.launch {
            val countryCode = locationService.getCountryCode()
            val finalEmotion = calculateEmotion(preEmotion = emotion, level = level)
            recognitionApi.getCards(finalEmotion, countryCode).onSuccess { cards ->
                val record = Record(System.currentTimeMillis(), finalEmotion, 0, cards.toString())
                database.updateRecord(record.timestamp, record)
                _recordMade.value = DataEvent(record)
            }.onFailure {
                _recordMade.value = DataEvent(null)
            }
        }
    }

    data class EmotionParameters(
        val first: String,
        val second: String,
        val third: String
    )

    fun calculateEmotion(preEmotion: PreEmotion, level: Int) = when (preEmotion) {
        PreEmotion.SAD -> {
            when (level) {
                0 -> context.getString(R.string.text_melancholy)
                1 -> context.getString(R.string.text_sadness)
                else -> context.getString(R.string.text_grief)
            }
        }
        PreEmotion.ANGRY -> {
            when (level) {
                0 -> context.getString(R.string.text_annoyance)
                1 -> context.getString(R.string.text_anger)
                else -> context.getString(R.string.text_rage)
            }
        }
        PreEmotion.FEAR -> {
            when (level) {
                0 -> context.getString(R.string.text_apprehension)
                1 -> context.getString(R.string.text_anxiety)
                else -> context.getString(R.string.text_panic)
            }
        }
        PreEmotion.DISGUST -> {
            when (level) {
                0 -> context.getString(R.string.text_shame)
                1 -> context.getString(R.string.text_guilt)
                else -> context.getString(R.string.text_disgust)
            }
        }
        else -> {
            when (level) {
                0 -> context.getString(R.string.text_neutral)
                1 -> context.getString(R.string.text_surprised)
                else -> context.getString(R.string.text_happy)
            }
        }
    }.apply { this.uppercase() }.let { Emotion.valueOf(it) }

    fun getEmotionParameters(emotion: PreEmotion) = when (emotion) {
        PreEmotion.SAD -> EmotionParameters(
            context.getString(R.string.text_melancholy),
            context.getString(R.string.text_sadness),
            context.getString(R.string.text_grief)
        )
        PreEmotion.ANGRY -> EmotionParameters(
            context.getString(R.string.text_annoyance),
            context.getString(R.string.text_anger),
            context.getString(R.string.text_rage)
        )
        PreEmotion.FEAR -> EmotionParameters(
            context.getString(R.string.text_apprehension),
            context.getString(R.string.text_anxiety),
            context.getString(R.string.text_panic)
        )
        PreEmotion.DISGUST -> EmotionParameters(
            context.getString(R.string.text_shame),
            context.getString(R.string.text_guilt),
            context.getString(R.string.text_disgust)
        )
        else -> EmotionParameters(
            context.getString(R.string.text_neutral),
            context.getString(R.string.text_surprised),
            context.getString(R.string.text_happy)
        )
    }
}