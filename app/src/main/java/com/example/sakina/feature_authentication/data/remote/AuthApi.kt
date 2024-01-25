package com.example.sakina.feature_authentication.data.remote

import com.example.sakina.feature_authentication.domain.mode.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.mode.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.mode.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("sendEmailConfirmation")
    suspend fun sendEmailConfirmation(@Body email: String)

    @POST("authenticate")
    suspend fun authenticate(@Body request: AuthenticateRequest): AuthenticateResponse

    @GET("isEmailDuplicated")
    suspend fun isEmailDuplicated(@Query("Email") email: String): Boolean


}