package com.example.sakina.feature_authentication.domain.use_case

import android.util.Log
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_authentication.domain.model.EmailRequest
import com.example.sakina.feature_authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CheckEmailDuplicationUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(emailDuplicationRequest: EmailRequest): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.isEmailDuplicated(emailDuplicationRequest)
            Log.d("Emem", "invoke: ${response.errorBody()} ")
            emit(Resource.Success(response.body()))

        } catch (e: HttpException) {
            Log.d("Reggggggg", "invoke: $e ")
            emit(Resource.Error("Registration failed: ${e.message()}", code = e.code()))
        } catch (e: IOException) {
            Log.d("Reggggggg", "invoke: $e ")
            emit(Resource.Error("Registration failed: ${e.message}", code = 1))
        } catch (e: Exception) {
            Log.d("Reggggggg", "invoke: $e ")
            emit(Resource.Error("Registration failed: ${e.message}", code = 2))
        }


    }

}