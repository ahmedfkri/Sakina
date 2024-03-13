package com.example.sakina.feature_account.domain.model

data class PersonalInfoRequest(
    val age: Int,
    val height: Int,
    val weight: Int,
    var diabetic: Boolean,
    var hypertension: Boolean,
    var hypotension: Boolean,
    var smoker: Boolean
)

