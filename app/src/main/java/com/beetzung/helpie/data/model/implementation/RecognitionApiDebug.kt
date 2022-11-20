package com.beetzung.helpie.data.model.implementation

import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.Emotion
import kotlinx.coroutines.delay

class RecognitionApiDebug : RecognitionApi {
    override suspend fun getRecognitionResult(image: ByteArray): Result<Emotion> {
        delay(1000)
        return Result.success(Emotion.values().random())
    }

    override suspend fun getCards(emotion: Emotion, level: Int, countryCode: String): Result<List<String>> {
        delay(1000)
        return Result.success(listOf("Card 1", "Card 2", "Card 3"))
    }
}