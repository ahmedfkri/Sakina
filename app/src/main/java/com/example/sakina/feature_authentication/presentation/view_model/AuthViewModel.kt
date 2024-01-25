package com.example.sakina.feature_authentication.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_authentication.domain.mode.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.mode.AuthenticateResponse
import com.example.sakina.feature_authentication.domain.mode.RegisterRequest
import com.example.sakina.feature_authentication.domain.use_case.AuthUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val authUseCases: AuthUseCases) : ViewModel() {


    private val _validationErrors = MutableStateFlow<Map<String, String?>>(emptyMap())
    val validationErrors: StateFlow<Map<String, String?>> = _validationErrors


    fun checkEmailDuplication(email: String)  : Flow<Resource<Boolean>>{
        return authUseCases.checkEmailDuplicationUseCase(email)
    }

    fun validateSignUp(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _validationErrors.value = authUseCases.validateSignUpUseCase(registerRequest)
        }
    }

    fun registerUser(registerRequest: RegisterRequest): Flow<Resource<Boolean>>{
        return authUseCases.registerUseCase(registerRequest)
    }
    fun authenticateUser(request: AuthenticateRequest): Flow<Resource<AuthenticateResponse>> {
        return authUseCases.authenticateUserUseCase(request)
    }

    fun sendEmailConfirmation(email: String) {
        viewModelScope.launch {
            authUseCases.sendEmailConfirmationUseCase(email)
        }
    }


}