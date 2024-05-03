package com.example.sakina.feature_account.domain.use_case

data class AccountUseCases(
    val changePasswordUseCase: ChangeAccountPasswordUseCase,
    val changeNameUseCase: ChangeAccountNameUseCase,
    val personalInfoUseCase: PersonalInfoUseCase,
    val getInformationUseCase: GetInformationUseCase,
    val getAccountDataUseCase: GetAccountDataUseCase
)
