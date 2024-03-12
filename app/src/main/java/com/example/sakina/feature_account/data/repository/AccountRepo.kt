package com.example.sakina.feature_account.data.repository

import com.example.sakina.core.data.remote.main_api.MainRetrofitClient
import com.example.sakina.feature_account.domain.model.ChangeNameRequest
import com.example.sakina.feature_account.domain.model.ChangePasswordRequest
import com.example.sakina.feature_account.domain.model.PersonalInfoRequest
import com.example.sakina.feature_account.domain.repositoy.AccountRepository
import retrofit2.Response

class AccountRepo:AccountRepository {
    override suspend fun changePassword(password: ChangePasswordRequest): Response<Unit> {
        return MainRetrofitClient.api.changePassword(password)
    }

    override suspend fun changeName(name: ChangeNameRequest): Response<Unit> {
        return MainRetrofitClient.api.changeName(name)
    }

    override suspend fun personalInfo(information: PersonalInfoRequest): Response<Unit> {
        return MainRetrofitClient.api.personalInfo(information)
    }

    override suspend fun getInformation(): PersonalInfoRequest {
        return MainRetrofitClient.api.getInformation()
    }
}
