package com.example.sakina.feature_account.domain.model

data class AccountDataDTO(
    val doctor: Doctor,
    val email: String,
    val firstName: String,
    val id: Int,
    val imageUrl: String,
    val joinedOn: String,
    val lastName: String,
    val role: String
)

fun AccountDataDTO.toAccountData(): AccountData {
    return AccountData(this.firstName, this.lastName, this.email)
}