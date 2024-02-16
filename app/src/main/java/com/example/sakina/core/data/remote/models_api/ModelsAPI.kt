package com.example.sakina.core.data.remote.models_api

import com.example.sakina.feature_heartChecking.domain.model.HeartResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ModelsAPI {
    @Multipart
    @POST("default/heartChecking")
    suspend fun heartChecking(@Part request: MultipartBody.Part,): HeartResponse
}