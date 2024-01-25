package com.example.sakina.feature_authentication.domain.repository

import com.example.sakina.feature_authentication.domain.mode.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.mode.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.mode.RegisterRequest

interface AuthRepository {

    suspend fun register(request: RegisterRequest)

    suspend fun sendEmailConfirmation(email: String)

    suspend fun authenticate(request: AuthenticateRequest): AuthenticateResponse

    suspend fun isEmailDuplicated(email: String): Boolean


}