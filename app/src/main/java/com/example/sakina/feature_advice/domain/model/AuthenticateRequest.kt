package com.example.sakina.feature_authentication.domain.model

data class AuthenticateRequest(
    val email: String,
    val password: String
)