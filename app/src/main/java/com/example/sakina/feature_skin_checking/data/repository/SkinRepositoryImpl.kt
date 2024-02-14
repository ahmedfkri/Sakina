package com.example.sakina.feature_skin_checking.data.repository

import android.net.Uri
import com.example.sakina.core.data.remote.models_api.ModelsRetrofitClient
import com.example.sakina.feature_skin_checking.domain.model.SkinResponse
import com.example.sakina.feature_skin_checking.domain.repository.SkinRepository
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class SkinRepositoryImpl():SkinRepository {
    override suspend fun checkSkin(file: MultipartBody.Part): SkinResponse {
        return ModelsRetrofitClient.api.checkSkin(file)
    }


}