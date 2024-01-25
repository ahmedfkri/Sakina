package com.example.sakina.feature_authentication.data.repository

import com.example.sakina.feature_authentication.data.remote.AuthRetrofitClient
import com.example.sakina.feature_authentication.domain.mode.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.mode.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.mode.RegisterRequest
import com.example.sakina.feature_authentication.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    override suspend fun register(request: RegisterRequest) {
        AuthRetrofitClient.api.register(request)
    }

    override suspend fun sendEmailConfirmation(email: String) {
        AuthRetrofitClient.api.sendEmailConfirmation(email)
    }

    override suspend fun authenticate(request: AuthenticateRequest): AuthenticateResponse {
        return AuthRetrofitClient.api.authenticate(request)
    }

    override suspend fun isEmailDuplicated(email: String): Boolean {
        return AuthRetrofitClient.api.isEmailDuplicated(email)
    }
}