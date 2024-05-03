package com.example.sakina.feature_account.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sakina.feature_account.domain.use_case.AccountUseCases

class AccountViewModelFactory(private val accountUseCase: AccountUseCases):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountViewModel(
            accountUseCase
        ) as T
    }
}
