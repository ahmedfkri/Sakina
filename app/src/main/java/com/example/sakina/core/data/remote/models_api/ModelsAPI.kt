package com.example.sakina.core.data.remote.models_api

import com.example.sakina.feature_heartChecking.domain.model.HeartResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import com.example.sakina.feature_skin_checking.domain.model.SkinResponse

interface ModelsAPI {

    @Multipart
    @POST("skinChecking")
    suspend fun checkSkin(
        @Part image: MultipartBody.Part
    ): SkinResponse

    @Multipart
    @POST("heartChecking")
    suspend fun heartChecking(@Part voice: MultipartBody.Part): HeartResponse
}