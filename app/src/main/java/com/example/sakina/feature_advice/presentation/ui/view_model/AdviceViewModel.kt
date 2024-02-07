package com.example.sakina.feature_advice.presentation.ui.view_model

import androidx.lifecycle.ViewModel
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_advice.domain.model.Advice
import com.example.sakina.feature_advice.domain.use_case.GetAdviceByIdUseCase
import com.example.sakina.feature_advice.domain.use_case.GetTotalAdvicesCountUseCase
import kotlinx.coroutines.flow.Flow

class AdviceViewModel(
    private val getTotalAdvicesCountUseCase: GetTotalAdvicesCountUseCase,
    private val getAdviceByIdUseCase: GetAdviceByIdUseCase,
) :
    ViewModel() {

    fun getTotalAdvicesCount(): Flow<Resource<Int>> {
        return getTotalAdvicesCountUseCase()
    }

    fun getAdvicesById(id: Int): Flow<Resource<Advice>>{
        return getAdviceByIdUseCase(id)
    }

}