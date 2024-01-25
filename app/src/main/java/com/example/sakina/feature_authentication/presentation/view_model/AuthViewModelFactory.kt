package com.example.sakina.feature_authentication.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sakina.feature_authentication.domain.use_case.AuthUseCases

class AuthViewModelFactory(
    private val authUseCases: AuthUseCases
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(
            authUseCases
        ) as T
    }


}