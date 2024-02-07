package com.example.sakina.feature_authentication.domain.repository

import com.example.sakina.feature_authentication.domain.model.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.model.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.model.RegisterRequest

interface AuthRepository {

    suspend fun register(request: RegisterRequest)

    suspend fun sendEmailConfirmation(email: String)

    suspend fun authenticate(request: AuthenticateRequest): AuthenticateResponse

    suspend fun isEmailDuplicated(email: String): Boolean


}