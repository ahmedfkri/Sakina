package com.example.sakina.feature_authentication.domain.mode

data class AuthenticateResponse(
    val email: String,
    val id: Int,
    val imageUrl: String,
    val jwtToken: String,
    val jwtTokenExpiresOn: String,
    val name: String,
    val refreshToken: String,
    val refreshTokenExpiresON: String,
    val role: String
)