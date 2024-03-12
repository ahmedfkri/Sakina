package com.example.sakina.feature_account.domain.model

data class PersonalInfoRequest(
    val age: Int,
    val height: Int,
    val weight: Int,
    val diabetic: Boolean,
    val hypertension: Boolean,
    val hypotension: Boolean,
    val smoker: Boolean
)