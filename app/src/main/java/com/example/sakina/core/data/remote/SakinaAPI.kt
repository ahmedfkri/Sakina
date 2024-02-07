package com.example.sakina.core.data.remote

import com.example.sakina.feature_advice.domain.model.Advice
import com.example.sakina.feature_advice.domain.model.Advices
import com.example.sakina.feature_authentication.domain.model.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.model.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SakinaAPI {

    //Authentication

    @POST("account/registerUser")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("account/sendEmailConfirmation")
    suspend fun sendEmailConfirmation(@Body email: String)

    @POST("account/authenticate")
    suspend fun authenticate(@Body request: AuthenticateRequest): AuthenticateResponse

    @POST("account/isEmailDuplicated")
    suspend fun isEmailDuplicated(@Body email: String): Boolean


    //Advice
    @GET("advices")
    suspend fun getAllAdvices(): Advices

    @GET("advices")
    suspend fun getAdviceById(@Query("Id") id: Int): Advice


}