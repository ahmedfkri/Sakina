package com.example.sakina.core.data.remote.main_api

import com.example.sakina.feature_advice.domain.model.Advice
import com.example.sakina.feature_advice.domain.model.Advices
import com.example.sakina.feature_authentication.domain.model.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.model.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.model.EmailRequest
import com.example.sakina.feature_authentication.domain.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainAPI {

    //Authentication

    @POST("account/registerUser")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("account/sendEmailConfirmation")
    suspend fun sendEmailConfirmation(@Body email: EmailRequest): Response<Unit>

    @POST("account/authenticate")
    suspend fun authenticate(@Body request: AuthenticateRequest): AuthenticateResponse

    @POST("account/isEmailDuplicated")
    suspend fun isEmailDuplicated(@Body emailDuplicationRequest: EmailRequest): Response<Boolean>


    //Advice
    @GET("advices")
    suspend fun getAllAdvices(): Advices

    @GET("advices/{Id}")
    suspend fun getAdviceById(@Path("Id") id: Int): Advice




}