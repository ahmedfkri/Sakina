package com.example.sakina.core.data.remote.main_api

import com.example.sakina.feature_authentication.domain.model.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.model.RefreshToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenAPI : MainAPI {

    @POST("account/requestJwt")
    suspend fun requestJwt(@Body refreshToken: RefreshToken): Response<AuthenticateResponse>
}