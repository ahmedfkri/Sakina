package com.example.sakina.feature_account.domain.use_case

import com.example.sakina.core.util.Resource
import com.example.sakina.feature_account.domain.model.PersonalInfoRequest
import com.example.sakina.feature_account.domain.repositoy.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetInformationUseCase(private val repo: AccountRepository) {
    operator fun invoke(): Flow<Resource<PersonalInfoRequest>> = flow {
        try {
            emit(Resource.Loading())
            val info=repo.getInformation()
            emit(Resource.Success(info))
        } catch (e: HttpException) {
            emit(Resource.Error("failed: ${e.message}", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed: ${e.message}", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("failed: ${e.message}", code = 2))
        }
    }
}