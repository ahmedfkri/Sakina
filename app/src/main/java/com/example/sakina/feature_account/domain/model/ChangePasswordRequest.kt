package com.example.sakina.feature_account.domain.model

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
