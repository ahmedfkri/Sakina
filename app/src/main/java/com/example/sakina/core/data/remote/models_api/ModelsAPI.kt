package com.example.sakina.core.data.remote.models_api

import com.example.sakina.feature_skin_checking.domain.model.SkinResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ModelsAPI {

    @Multipart
    @POST("skinChecking")
    suspend fun checkSkin(
        @Part image: MultipartBody.Part,
    ): SkinResponse


}