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
data class YouTubeCommentRequest(
    val snippet: YouTubeCommentSnippet
)

@Serializable
data class YouTubeCommentSnippet(
    val videoId: String,
    val topLevelComment: YouTubeTopLevelComment
)

@Serializable
data class YouTubeTopLevelComment(
    val snippet: YouTubeTextOriginal
)

@Serializable
data class YouTubeTextOriginal(
    val textOriginal: String
)

@Serializable
data class YouTubeCommentResponse(
    val id: String? = null,
    val error: YouTubeError? = null
)

@Serializable
data class YouTubeError(
    val code: Int? = null,
    val message: String? = null
)

interface YouTubeApi {
    @POST("youtube/v3/commentThreads")
    suspend fun insertCommentThread(
        @Query("part") part: String = "snippet",
        @Header("Authorization") accessToken: String,
        @Body body: YouTubeCommentRequest
    ): YouTubeCommentResponse
}

object YouTubeClient {
    private const val BASE_URL = "https://www.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: YouTubeApi by lazy {
        val json = Json { ignoreUnknownKeys = true; isLenient = true }
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(YouTubeApi::class.java)
    }
}
