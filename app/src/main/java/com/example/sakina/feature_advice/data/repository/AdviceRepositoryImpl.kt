package com.example.sakina.feature_advice.data.repository

import com.example.sakina.core.data.remote.RetrofitClient
import com.example.sakina.feature_advice.domain.model.Advice
import com.example.sakina.feature_advice.domain.model.Advices
import com.example.sakina.feature_advice.domain.repository.AdviceRepository

class AdviceRepositoryImpl : AdviceRepository {

    override suspend fun getAllAdvices(): Advices {
        return RetrofitClient.api.getAllAdvices()
    }

    override suspend fun getAdviceById(id: Int): Advice {
        return RetrofitClient.api.getAdviceById(id)
    }
}