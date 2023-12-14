package com.example.sakina.feature_authentication.domain.mode

data class AuthenticateRequest(
    val email: String,
    val password: String
)