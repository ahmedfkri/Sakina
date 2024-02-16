package com.example.sakina.feature_heartChecking.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_heartChecking.domain.model.HeartResponse
import com.example.sakina.feature_heartChecking.domain.useCase.HeartCheckingVoiceUseCase
import kotlinx.coroutines.flow.Flow
import java.io.File

class HeartViewModel(private val heartCheckingUseCase: HeartCheckingVoiceUseCase):ViewModel() {
    fun heartVoice(file:File):Flow<Resource<HeartResponse>>{
        return heartCheckingUseCase(file)
    }
}