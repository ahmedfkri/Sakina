package com.example.sakina.feature_account.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_account.domain.model.ChangeNameRequest
import com.example.sakina.feature_account.domain.model.ChangePasswordRequest
import com.example.sakina.feature_account.domain.use_case.AccountUseCase
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AccountViewModel(private val accountUseCase:AccountUseCase):ViewModel() {

    var accountData:MutableLiveData<Response<Unit>> = MutableLiveData()
    fun changePassword(password: ChangePasswordRequest): Flow<Resource<Unit>> {
        return accountUseCase.changePasswordUseCase(password)
    }

    fun changeName(name: ChangeNameRequest): Flow<Resource<Unit>> {
        return accountUseCase.changeNameUseCase(name)

    }
}
