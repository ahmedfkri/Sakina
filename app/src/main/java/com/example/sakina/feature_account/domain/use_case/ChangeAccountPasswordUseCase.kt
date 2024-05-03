package com.example.sakina.feature_account.domain.use_case

import com.example.sakina.core.util.Resource
import com.example.sakina.feature_account.domain.model.ChangePasswordRequest
import com.example.sakina.feature_account.domain.repositoy.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ChangeAccountPasswordUseCase (private val repo:AccountRepository){
    operator fun invoke(password: ChangePasswordRequest): Flow<Resource<Unit>> = flow {

        try {
            emit(Resource.Loading())
            repo.changePassword(password)
            emit(Resource.Success(null))

        } catch (e: HttpException) {
            emit(Resource.Error("Registration failed: $e", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("Registration failed: $e", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("Registration failed: $e", code = 2))
        }

    }

}
