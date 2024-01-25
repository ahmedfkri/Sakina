package com.example.sakina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.sakina.databinding.ActivityMainBinding
import com.example.sakina.feature_authentication.data.repository.AuthRepositoryImpl
import com.example.sakina.feature_authentication.domain.repository.AuthRepository
import com.example.sakina.feature_authentication.domain.use_case.AuthUseCases
import com.example.sakina.feature_authentication.domain.use_case.AuthenticateUserUseCase
import com.example.sakina.feature_authentication.domain.use_case.CheckEmailDuplicationUseCase
import com.example.sakina.feature_authentication.domain.use_case.RegisterUseCase
import com.example.sakina.feature_authentication.domain.use_case.SendEmailConfirmationUseCase
import com.example.sakina.feature_authentication.domain.use_case.ValidateSignUpUseCase
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModel
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authRepository = AuthRepositoryImpl()

        val authenticateUserUseCase = AuthenticateUserUseCase(authRepository)
        val checkEmailDuplicationUseCase = CheckEmailDuplicationUseCase(authRepository)
        val registerUseCase = RegisterUseCase(authRepository)
        val sendEmailConfirmationUseCase = SendEmailConfirmationUseCase(authRepository)
        val validateSignUpUseCase = ValidateSignUpUseCase()

        val authUseCases = AuthUseCases(
            authenticateUserUseCase = authenticateUserUseCase,
            checkEmailDuplicationUseCase = checkEmailDuplicationUseCase,
            registerUseCase = registerUseCase,
            sendEmailConfirmationUseCase = sendEmailConfirmationUseCase,
            validateSignUpUseCase = validateSignUpUseCase
        )

        authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(authUseCases)
        ).get(AuthViewModel::class.java)
    }
}