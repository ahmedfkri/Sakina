package com.example.sakina.feature_account.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_account.domain.model.AccountData
import com.example.sakina.feature_account.domain.model.ChangeNameRequest
import com.example.sakina.feature_account.domain.model.ChangePasswordRequest
import com.example.sakina.feature_account.domain.model.PersonalInfoRequest
import com.example.sakina.feature_account.domain.use_case.AccountUseCases
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AccountViewModel(private val accountUseCase: AccountUseCases) : ViewModel() {


    val currentName: MutableLiveData<String> = MutableLiveData()
    val currentPass: MutableLiveData<String> = MutableLiveData()


    var accountData: MutableLiveData<Response<Unit>> = MutableLiveData()

    fun changePassword(password: ChangePasswordRequest): Flow<Resource<Unit>> {
        return accountUseCase.changePasswordUseCase(password)
    }

    fun changeName(name: ChangeNameRequest): Flow<Resource<Unit>> {
        return accountUseCase.changeNameUseCase(name)
    }

    fun personalInfo(information: PersonalInfoRequest): Flow<Resource<Unit>> {
        return accountUseCase.personalInfoUseCase(information)
    }

    fun getInformation(): Flow<Resource<PersonalInfoRequest>> {
        return accountUseCase.getInformationUseCase()
    }

    fun getAccountData(): Flow<Resource<AccountData>> {
        return accountUseCase.getAccountDataUseCase()
    }
}
