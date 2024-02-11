package com.example.sakina.feature_authentication.domain.use_case


import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_authentication.domain.model.RegisterRequest
import com.example.sakina.feature_authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RegisterUseCase(private val repository: AuthRepository) {

    operator fun invoke(registerRequest: RegisterRequest): Flow<Resource<Unit>> = flow {

        try {
            emit(Resource.Loading())

            repository.register(registerRequest)

            MySharedPref.putBool(Constant.SIGNED_UP, true)
            MySharedPref.putString(Constant.USER_EMAIL, registerRequest.email)
            MySharedPref.putString(Constant.USER_PASS, registerRequest.password)
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
