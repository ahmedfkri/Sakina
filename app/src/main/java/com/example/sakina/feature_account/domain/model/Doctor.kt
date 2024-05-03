package com.example.sakina.feature_account.domain.model

data class Doctor(
    val biography: String,
    val consultFee: Int,
    val experience: Int,
    val languages: List<Language>,
    val speciality: Speciality
)