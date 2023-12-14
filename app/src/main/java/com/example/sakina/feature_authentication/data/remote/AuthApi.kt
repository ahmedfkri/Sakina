package com.example.sakina.feature_authentication.data.remote

import com.example.sakina.feature_authentication.domain.mode.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.mode.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.mode.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest)

    @POST("sendEmailConfirmation")
    suspend fun endEmailConfirmation(@Body email: String)

    @POST("authenticate")
    suspend fun authenticate(@Body request: AuthenticateRequest) : AuthenticateResponse



}