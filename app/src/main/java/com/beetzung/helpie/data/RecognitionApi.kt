package com.beetzung.helpie.data

import com.beetzung.helpie.data.model.ApiEmotion
import com.beetzung.helpie.data.model.Emotion

interface RecognitionApi {
    suspend fun getRecognitionResult(imageUrl: String): Result<ApiEmotion?>

    suspend fun getCards(emotion: Emotion, countryCode: String): Result<List<String>>

    suspend fun getRegionInfo(countryCode: String): Result<String>
}