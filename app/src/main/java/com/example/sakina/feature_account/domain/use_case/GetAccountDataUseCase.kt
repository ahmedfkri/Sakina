package com.example.sakina.feature_account.domain.use_case

import com.example.sakina.core.util.Resource
import com.example.sakina.feature_account.domain.model.AccountData
import com.example.sakina.feature_account.domain.model.toAccountData
import com.example.sakina.feature_account.domain.repositoy.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetAccountDataUseCase(private val repo: AccountRepository) {

    operator fun invoke(): Flow<Resource<AccountData>> = flow {
        try {
            emit(Resource.Loading())
            val accountData = repo.getAccountData()
            emit(Resource.Success(accountData.toAccountData()))
        } catch (e: HttpException) {
            emit(Resource.Error("failed: ${e.message}", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed: ${e.message}", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("failed: ${e.message}", code = 2))
        }
    }
}