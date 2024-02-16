package com.example.sakina.feature_heartChecking.domain.model

import android.accounts.AuthenticatorDescription

data class HeartResponse(
    val label:String,
    val description: String,
    val advice:String
)