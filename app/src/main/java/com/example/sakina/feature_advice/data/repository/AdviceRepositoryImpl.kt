package com.example.sakina.feature_advice.data.repository

import com.example.sakina.core.data.remote.main_api.MainRetrofitClient
import com.example.sakina.feature_advice.domain.model.Advice
import com.example.sakina.feature_advice.domain.model.Advices
import com.example.sakina.feature_advice.domain.repository.AdviceRepository

class AdviceRepositoryImpl : AdviceRepository {

    override suspend fun getAllAdvices(): Advices {
        return MainRetrofitClient.api.getAllAdvices()
    }

    override suspend fun getAdviceById(id: Int): Advice {
        return MainRetrofitClient.api.getAdviceById(id)
    }
}