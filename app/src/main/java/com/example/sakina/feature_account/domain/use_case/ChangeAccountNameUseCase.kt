package com.example.sakina.feature_account.domain.use_case

import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_account.domain.model.ChangeNameRequest
import com.example.sakina.feature_account.domain.model.ChangePasswordRequest
import com.example.sakina.feature_account.domain.repositoy.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ChangeAccountNameUseCase(private val repo:AccountRepository) {
    operator fun invoke(name: ChangeNameRequest): Flow<Resource<Unit>> = flow {

        try {
            emit(Resource.Loading())
            repo.changeName(name)
            MySharedPref.putString(Constant.FIRST_NAME, name.firstName)
            MySharedPref.putString(Constant.LAST_NAME, name.lastName)
            emit(Resource.Success(null))

        } catch (e: HttpException) {
            emit(Resource.Error("change name $e", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("change name: $e", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("change name: $e", code = 2))
        }

    }
}
