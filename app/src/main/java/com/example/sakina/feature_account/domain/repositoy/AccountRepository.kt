package com.example.sakina.feature_account.domain.repositoy

import com.example.sakina.feature_account.domain.model.ChangeNameRequest
import com.example.sakina.feature_account.domain.model.ChangePasswordRequest
import retrofit2.Response

interface AccountRepository {
    suspend fun changePassword(password: ChangePasswordRequest) : Response<Unit>

    suspend fun changeName(name:ChangeNameRequest) : Response<Unit>
}
