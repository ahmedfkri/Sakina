package com.example.sakina.feature_heartChecking.domain.repository

import com.example.sakina.feature_heartChecking.domain.model.HeartResponse
import okhttp3.MultipartBody

interface HeartRepo {
    suspend fun heartChecking(file: MultipartBody.Part): HeartResponse
}