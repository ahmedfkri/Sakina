package com.example.sakina.feature_heartChecking.data.repository

import com.example.sakina.core.data.remote.models_api.ModelsRetrofitClient
import com.example.sakina.feature_heartChecking.domain.model.HeartResponse
import com.example.sakina.feature_heartChecking.domain.repository.HeartRepo
import okhttp3.MultipartBody

class HeartCheckingRepo:HeartRepo {
    override suspend fun heartChecking(file: MultipartBody.Part): HeartResponse {
        return ModelsRetrofitClient.api.checkHeart(file)
    }
}