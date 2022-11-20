package com.beetzung.helpie.data

import com.beetzung.helpie.data.model.ApiEmotion
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.data.model.PreEmotion

interface RecognitionApi {
    suspend fun getRecognitionResult(image: ByteArray): Result<ApiEmotion?>

    suspend fun getCards(emotion: Emotion, countryCode: String): Result<List<String>>
}