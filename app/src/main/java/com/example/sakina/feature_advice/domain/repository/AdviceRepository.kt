package com.example.sakina.feature_advice.domain.repository

import com.example.sakina.feature_advice.domain.model.Advice
import com.example.sakina.feature_advice.domain.model.Advices

interface AdviceRepository {

    suspend fun getAllAdvices(): Advices

    suspend fun getAdviceById(id: Int): Advice
}