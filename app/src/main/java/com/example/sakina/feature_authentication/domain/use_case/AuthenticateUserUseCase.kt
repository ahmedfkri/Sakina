package com.example.sakina.feature_authentication.domain.use_case

import android.util.Log
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_authentication.domain.mode.AuthErrorResponse
import com.example.sakina.feature_authentication.domain.mode.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.mode.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class AuthenticateUserUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(request: AuthenticateRequest): Flow<Resource<AuthenticateResponse>> =
        flow {
            try {
                val response = repository.authenticate(request)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                val errorJSON = e.response()?.errorBody()?.string() ?: "{}"
                val errorResponse = Gson().fromJson(errorJSON, AuthErrorResponse::class.java)
                emitError(errorResponse)

            } catch (e: Exception) {
                val errorResponse = AuthErrorResponse( message = "Unexpected error", statusCode = 0)
                emitError(errorResponse)
            }
        }.catch { e->


        }

    private suspend fun FlowCollector<Resource<AuthenticateResponse>>.emitError(errorResponse: AuthErrorResponse) {
        emit(
            Resource.Error(
                code = errorResponse.statusCode,
                message = errorResponse.message
            )
        )
    }

}