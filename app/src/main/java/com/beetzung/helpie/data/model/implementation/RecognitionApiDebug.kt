package com.beetzung.helpie.data.model.implementation

import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.Emotion

class RecognitionApiDebug: RecognitionApi {
    override suspend fun getRecognitionResult(image: ByteArray): Result<Emotion> {
           return Result.success(Emotion.values().random())
    }
}