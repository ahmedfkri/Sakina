package com.example.sakina.feature_advice.domain.use_case

import com.example.sakina.core.util.Resource
import com.example.sakina.feature_advice.domain.model.Advice
import com.example.sakina.feature_advice.domain.repository.AdviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetAdviceByIdUseCase(private val repository: AdviceRepository) {

    operator fun invoke(id: Int): Flow<Resource<Advice>> = flow {
        try {
            emit(Resource.Loading())
            val advice = repository.getAdviceById(id)
            emit(Resource.Success(data =advice))

        }catch (e: HttpException) {
            emit(Resource.Error("failed: ${e.message}", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed: ${e.message}", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("failed: ${e.message}", code = 2))
        }

    }
}