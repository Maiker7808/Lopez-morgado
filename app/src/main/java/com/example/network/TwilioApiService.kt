package com.example.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

@Serializable
data class TwilioMessageResponse(
    val sid: String? = null,
    val message: String? = null,
    val code: Int? = null,
    val more_info: String? = null
)

interface TwilioApi {
    @FormUrlEncoded
    @POST("2010-04-01/Accounts/{account_sid}/Messages.json")
    suspend fun sendWhatsAppMessage(
        @Path("account_sid") accountSid: String,
        @Header("Authorization") authorization: String,
        @Field("From") from: String,
        @Field("To") to: String,
        @Field("Body") body: String
    ): TwilioMessageResponse
}

object TwilioClient {
    private const val BASE_URL = "https://api.twilio.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: TwilioApi by lazy {
        val json = Json { ignoreUnknownKeys = true; isLenient = true }
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TwilioApi::class.java)
    }
}
