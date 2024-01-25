package com.example.sakina.feature_authentication.domain.use_case

import com.example.sakina.feature_authentication.data.remote.AuthRetrofitClient
import com.example.sakina.feature_authentication.domain.repository.AuthRepository

class SendEmailConfirmationUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(email: String) {
        repository.sendEmailConfirmation(email)
    }

}