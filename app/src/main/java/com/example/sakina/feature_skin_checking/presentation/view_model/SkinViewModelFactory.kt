package com.example.sakina.feature_skin_checking.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sakina.feature_skin_checking.domain.use_case.UploadImageUseCase

class SkinViewModelFactory(private val uploadImageUseCase: UploadImageUseCase ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SkinCheckingViewModel(
            uploadImageUseCase
        ) as T
    }
}