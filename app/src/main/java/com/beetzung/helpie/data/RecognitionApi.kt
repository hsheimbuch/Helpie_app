package com.beetzung.helpie.data

import com.beetzung.helpie.data.model.Emotion

interface RecognitionApi {
    suspend fun getRecognitionResult(image: ByteArray): Result<Emotion>

    suspend fun getCards(emotion: Emotion, level: Int, countryCode: String): Result<List<String>>
}