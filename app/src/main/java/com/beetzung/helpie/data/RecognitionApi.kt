package com.beetzung.helpie.data

import com.beetzung.helpie.data.model.Emotion

interface RecognitionApi {
    suspend fun getRecognitionResult(image: ByteArray): Result<Emotion>
}