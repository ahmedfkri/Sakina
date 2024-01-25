package com.example.sakina.feature_authentication.domain.use_case

data class AuthUseCases(
    val authenticateUserUseCase: AuthenticateUserUseCase,
    val checkEmailDuplicationUseCase: CheckEmailDuplicationUseCase,
    val registerUseCase: RegisterUseCase,
    val sendEmailConfirmationUseCase: SendEmailConfirmationUseCase,
    val validateSignUpUseCase: ValidateSignUpUseCase,
) {
}