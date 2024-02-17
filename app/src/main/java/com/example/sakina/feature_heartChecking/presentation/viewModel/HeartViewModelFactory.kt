package com.example.sakina.feature_heartChecking.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sakina.feature_heartChecking.domain.useCase.HeartCheckingVoiceUseCase

class HeartViewModelFactory(private val heartCheckingUseCase: HeartCheckingVoiceUseCase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeartViewModel(
            heartCheckingUseCase
        ) as T
    }
}