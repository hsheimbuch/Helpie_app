package com.beetzung.helpie.data.model.implementation

import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.Emotion

class RecognitionApiImpl: RecognitionApi {
    override suspend fun getRecognitionResult(image: ByteArray): Result<Emotion> {
        TODO("Not yet implemented")
    }

    override suspend fun getCards(emotion: Emotion, level: Int, countryCode: String): Result<List<String>> {
        TODO("Not yet implemented")
    }
}