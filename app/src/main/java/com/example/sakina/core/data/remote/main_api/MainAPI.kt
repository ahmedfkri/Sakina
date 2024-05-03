package com.example.sakina.core.data.remote.main_api

import com.example.sakina.feature_account.domain.model.AccountDataDTO
import com.example.sakina.feature_account.domain.model.ChangeNameRequest
import com.example.sakina.feature_account.domain.model.ChangePasswordRequest
import com.example.sakina.feature_account.domain.model.PersonalInfoRequest
import com.example.sakina.feature_advice.domain.model.Advice
import com.example.sakina.feature_advice.domain.model.Advices
import com.example.sakina.feature_authentication.domain.model.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.model.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.model.EmailRequest
import com.example.sakina.feature_authentication.domain.model.RefreshToken
import com.example.sakina.feature_authentication.domain.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MainAPI {

    //Authentication

    /* @POST("account/requestJwt")
     suspend fun requestJwt(@Body refreshToken: RefreshToken): Response<AuthenticateResponse>*/

    @POST("account/registerUser")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("account/sendEmailConfirmation")
    suspend fun sendEmailConfirmation(@Body email: EmailRequest): Response<Unit>

    @POST("account/authenticate")
    suspend fun authenticate(@Body request: AuthenticateRequest): AuthenticateResponse

    @POST("account/isEmailDuplicated")
    suspend fun isEmailDuplicated(@Body emailDuplicationRequest: EmailRequest): Response<Boolean>

    //Account
    @POST("account/changePassword")
    suspend fun changePassword(@Body password: ChangePasswordRequest): Response<Unit>

    @PUT("account/user")
    suspend fun changeName(@Body name: ChangeNameRequest): Response<Unit>


    //MedicalInformation
    @PUT("medicalInfos")
    suspend fun personalInfo(@Body information: PersonalInfoRequest): Response<Unit>

    @GET("medicalInfos")
    suspend fun getInformation(): PersonalInfoRequest


    @GET("account")
    suspend fun getAccountData(): AccountDataDTO


    //Advice
    @GET("advices")
    suspend fun getAllAdvices(): Advices

    @GET("advices/{Id}")
    suspend fun getAdviceById(@Path("Id") id: Int): Advice


}