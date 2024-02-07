package com.example.sakina.feature_advice.domain.use_case

import com.example.sakina.core.util.Resource
import com.example.sakina.feature_advice.domain.repository.AdviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetTotalAdvicesCountUseCase(private val repository: AdviceRepository) {

    operator fun invoke(): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading())
            val advices = repository.getAllAdvices()
            val count = advices.totalCount
            emit(Resource.Success(count))


        } catch (e: HttpException) {
            emit(Resource.Error("failed: ${e.message}", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed: ${e.message}", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("failed: ${e.message}", code = 2))
        }
    }
}