package com.beetzung.helpie.data.model.implementation

import android.util.Log
import androidx.annotation.Keep
import com.beetzung.helpie.core.TAG
import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.ApiEmotion
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.data.model.PreEmotion
import com.beetzung.helpie.data.model.apiEmotionFromNumber
import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Query

class RecognitionApiImpl: RecognitionApi {
    private val api = Retrofit.Builder()
        .baseUrl("http://192.168.1.21:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RecognitionApiScheme::class.java)


    override suspend fun getRecognitionResult(image: ByteArray): Result<ApiEmotion?> {
        try {
            return api
                .getRecognitionResult(image.let {
                    RequestBody.create(
                        okhttp3.MediaType.parse("image/jpeg"),
                        it
                    )
                })
                .body().also { Log.d(TAG, "getRecognitionResult: $it") }
                ?.emotion
                ?.let {
                    Result.success(apiEmotionFromNumber(it))
                } ?: Result.failure(Exception("Error")) //TODO add custom exception
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getCards(emotion: Emotion, countryCode: String): Result<List<String>> {
        TODO("Not yet implemented")
    }
}

@Keep
interface RecognitionApiScheme {
    @HTTP(method = "POST", path = "/analyze", hasBody = true)
//    @GET("analyze")
    suspend fun getRecognitionResult(@Body body: RequestBody): Response<RecognitionResult>

    @GET("get_cards")
    suspend fun getCards(@Query("emotion") emotion: PreEmotion, @Query("level") level: Int, @Query("country_code") countryCode: String): Response<FinalResult>

}

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