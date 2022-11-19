package com.beetzung.helpie.data.model.implementation

import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.Emotion
import kotlinx.coroutines.delay

class RecognitionApiDebug : RecognitionApi {
    override suspend fun getRecognitionResult(image: ByteArray): Result<Emotion> {
        delay(3000)
        return Result.success(Emotion.values().random())
    }
}