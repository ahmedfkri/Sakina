package com.example.sakina.feature_authentication.domain.mode

data class AuthenticateResponse(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val imageUrl: String?,
    val jwtToken: String,
    val jwtTokenExpiresOn: String,
    val refreshToken: String,
    val refreshTokenExpiresON: String
)