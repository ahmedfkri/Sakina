package com.example.sakina.core.data.remote.models_api

import com.example.sakina.feature_chat.domain.model.ChatMessage
import com.example.sakina.feature_chat.domain.model.MessageRequest
import com.example.sakina.feature_chat.domain.model.MessageResponse
import com.example.sakina.feature_heartChecking.domain.model.HeartResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import com.example.sakina.feature_skin_checking.domain.model.SkinResponse
import retrofit2.http.Body

interface ModelsAPI {

    @Multipart
    @POST("skinChecking")
    suspend fun checkSkin(
        @Part image: MultipartBody.Part
    ): SkinResponse

    @Multipart
    @POST("heartChecking")
    suspend fun checkHeart(@Part voice: MultipartBody.Part): HeartResponse

    @POST("chatBot")
    suspend fun sendMessage(@Body message: MessageRequest): MessageResponse
}