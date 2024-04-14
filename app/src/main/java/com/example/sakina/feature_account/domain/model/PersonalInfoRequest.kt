package com.example.sakina.feature_account.domain.model

data class PersonalInfoRequest(
    var age: Int?,
    var height: Int?,
    var weight: Int?,
    var diabetic: Boolean?,
    var hypertension: Boolean?,
    var hypotension: Boolean?,
    var smoker: Boolean?
)
