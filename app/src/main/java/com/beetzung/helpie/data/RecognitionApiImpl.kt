package com.beetzung.helpie.data

import com.beetzung.helpie.data.model.Emotion

class RecognitionApiImpl: RecognitionApi {
    override suspend fun getRecognitionResult(image: ByteArray): Result<Emotion> {
        TODO("Not yet implemented")
    }
}