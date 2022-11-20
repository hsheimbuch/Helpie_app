package com.beetzung.helpie.data.model.implementation

import android.util.Log
import androidx.annotation.Keep
import com.beetzung.helpie.core.TAG
import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.ApiEmotion
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.data.model.apiEmotionFromNumber
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class RecognitionApiImpl : RecognitionApi {
    private val api = Retrofit.Builder()
        .baseUrl("http://192.168.1.21:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RecognitionApiScheme::class.java)


    override suspend fun getRecognitionResult(imageUrl: String): Result<ApiEmotion?> {
        return try {
            val result = api.getRecognitionResult(imageUrl)
            if (result.isSuccessful) {
                result.body().also { Log.d(TAG, "getRecognitionResult: $it") }
                    ?.emotion
                    ?.let {
                        Result.success(apiEmotionFromNumber(it))
                    } ?: Result.failure(Exception("null response"))
            } else {
                val error = result.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Log.e(TAG, "getRecognitionResult: ", e)
            Result.failure(e)
        }
    }

    override suspend fun getCards(emotion: Emotion, countryCode: String): Result<List<String>> {
        return try {
            val result = api.getCards(emotion.ordinal)
            if (result.isSuccessful) {
                result.body()
                    ?.let {
                        val list = listOf(it.`1`, it.`2`, it.`3`)
                        Result.success(list)
                    } ?: let {
                    val error = result.errorBody()?.string() ?: "Null response"
                    Result.failure(Exception(error))
                }
            } else {
                val error = result.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Log.e(TAG, "getCards: ", e)
            Result.failure(e)
        }
    }

    override suspend fun getRegionInfo(countryCode: String): Result<String> {
        val result = api.getRegionInfo(countryCode)
        return if (result.isSuccessful) {
            result.body()
                ?.let {
                    Result.success(it.toString())
                } ?: let {
                val error = result.errorBody()?.string() ?: "Null response"
                Result.failure(Exception(error))
            }
        } else {
            val error = result.errorBody()?.string() ?: "Unknown error"
            Result.failure(Exception(error))
        }
    }
}

@Keep
interface RecognitionApiScheme {
    @GET("/analyze")
    suspend fun getRecognitionResult(@Query("file") imageRaw: String): Response<RecognitionResult>

    @GET("/result")
    suspend fun getCards(@Query("result") result: Int): Response<FinalResult>

    @GET("/location")
    suspend fun getRegionInfo(@Query("location") location: String): Response<LocationResult>

}

@Keep
data class RecognitionRequest(
    @SerializedName("file")
    val file: List<String>
)

@Keep
data class RecognitionResult(@SerializedName("emotion") val emotion: Int)

@Keep
data class FinalResult(
    @SerializedName("emotion")
    val emotion: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("time")
    val time: Int,
    val `1`: String,
    val `2`: String,
    val `3`: String,
    val `4`: String,
)

@Keep
data class LocationResult(
    @SerializedName("location")
    val location: List<List<String>>
)