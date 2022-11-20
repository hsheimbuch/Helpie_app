package com.beetzung.helpie.data.model.implementation

import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.ApiEmotion
import com.beetzung.helpie.data.model.Emotion
import kotlinx.coroutines.delay

class RecognitionApiDebug : RecognitionApi {
    override suspend fun getRecognitionResult(imageUrl: String): Result<ApiEmotion?> {
        delay(1000)
        return Result.success(ApiEmotion.values().random())
    }

    override suspend fun getCards(emotion: Emotion, countryCode: String): Result<List<String>> {
        delay(1000)
        return Result.success(listOf("Card 1", "Card 2", "Card 3"))
    }

    override suspend fun getRegionInfo(countryCode: String): Result<String> {
        return Result.success("региональная инфа")
    }
}