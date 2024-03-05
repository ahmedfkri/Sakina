package com.example.sakina.feature_account.domain.use_case

import com.example.sakina.feature_authentication.domain.use_case.CheckEmailDuplicationUseCase
import com.example.sakina.feature_authentication.domain.use_case.RegisterUseCase

data class AccountUseCase (
    val changePasswordUseCase: ChangeAccountPasswordUseCase,
    val changeNameUseCase: ChangeAccountNameUseCase
)
