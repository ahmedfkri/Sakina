package com.example.sakina.feature_authentication.domain.repository

import com.example.sakina.feature_authentication.domain.model.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.model.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.model.EmailRequest
import com.example.sakina.feature_authentication.domain.model.RegisterRequest
import retrofit2.Response

interface AuthRepository {

    suspend fun register(request: RegisterRequest): Response<Unit>

    suspend fun sendEmailConfirmation(email: EmailRequest) : Response<Unit>

    suspend fun authenticate(request: AuthenticateRequest): AuthenticateResponse

    suspend fun isEmailDuplicated(emailDuplicationRequest: EmailRequest) : Response<Boolean>


}