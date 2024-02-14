package com.example.sakina.feature_skin_checking.domain.repository

import com.example.sakina.feature_skin_checking.domain.model.SkinResponse
import okhttp3.MultipartBody


interface SkinRepository {

    suspend fun checkSkin(file: MultipartBody.Part) : SkinResponse
}