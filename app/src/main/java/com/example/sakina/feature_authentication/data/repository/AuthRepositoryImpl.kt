package com.example.sakina.feature_authentication.data.repository

import com.example.sakina.core.data.remote.RetrofitClient
import com.example.sakina.feature_authentication.domain.repository.AuthRepository
import com.example.sakina.feature_authentication.domain.model.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.model.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.model.RegisterRequest

class AuthRepositoryImpl : AuthRepository {

    override suspend fun register(request: RegisterRequest) {
        RetrofitClient.api.register(request)
    }

    override suspend fun sendEmailConfirmation(email: String) {
        RetrofitClient.api.sendEmailConfirmation(email)
    }

    override suspend fun authenticate(request: AuthenticateRequest): AuthenticateResponse {
        return RetrofitClient.api.authenticate(request)
    }

    override suspend fun isEmailDuplicated(email: String): Boolean {
        return RetrofitClient.api.isEmailDuplicated(email)
    }
}