package com.example.sakina.feature_account.domain.model

data class ChangePasswordRequest(
    var oldPassword:String,
    var newPassword:String
)
