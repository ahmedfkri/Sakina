package com.example.sakina.feature_authentication.domain.mode

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)