package com.example.sakina.feature_advice.presentation.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sakina.feature_advice.domain.use_case.GetAdviceByIdUseCase
import com.example.sakina.feature_advice.domain.use_case.GetTotalAdvicesCountUseCase

class AdviceViewModelFactory(
    private val advicesCountUseCase: GetTotalAdvicesCountUseCase,
    private val getAdviceByIdUseCase: GetAdviceByIdUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AdviceViewModel(
            advicesCountUseCase,
            getAdviceByIdUseCase
        ) as T
    }
}