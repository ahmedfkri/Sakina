package com.example.sakina.feature_authentication.domain.use_case

import com.example.sakina.core.util.Resource
import com.example.sakina.feature_authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CheckEmailDuplicationUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(email: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.isEmailDuplicated(email)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message, code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error(e.message, code = 1))

        }


    }

}