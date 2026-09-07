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
data class FacebookPostResponse(
    val id: String? = null,
    val error: FacebookError? = null
)

@Serializable
data class FacebookError(
    val message: String? = null,
    val type: String? = null,
    val code: Int? = null
)

interface FacebookApi {
    @FormUrlEncoded
    @POST("v20.0/{page_id}/feed")
    suspend fun publishToFeed(
        @Path("page_id") pageId: String,
        @Field("message") message: String,
        @Field("access_token") accessToken: String,
        @Field("link") link: String? = null
    ): FacebookPostResponse
}

object FacebookClient {
    private const val BASE_URL = "https://graph.facebook.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: FacebookApi by lazy {
        val json = Json { ignoreUnknownKeys = true; isLenient = true }
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(FacebookApi::class.java)
    }
}
