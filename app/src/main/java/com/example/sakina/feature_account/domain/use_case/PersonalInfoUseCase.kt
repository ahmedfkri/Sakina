package com.example.sakina.feature_account.domain.use_case

import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_account.domain.model.PersonalInfoRequest
import com.example.sakina.feature_account.domain.repositoy.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PersonalInfoUseCase(private val repo: AccountRepository) {
    operator fun invoke(information: PersonalInfoRequest): Flow<Resource<Unit>> = flow {

        try {
            emit(Resource.Loading())
            repo.personalInfo(information)
            MySharedPref.putInt(Constant.AGE, information.age!!)
            MySharedPref.putInt(Constant.HEIGHT, information.hight!!)
            MySharedPref.putInt(Constant.WEIGHT, information.wight!!)
            MySharedPref.putBool(Constant.DIABETIC, information.diabetic!!)
            MySharedPref.putBool(Constant.HYPERTENSION, information.hypertension!!)
            MySharedPref.putBool(Constant.HYPOTENSION, information.hypotension!!)
            MySharedPref.putBool(Constant.IS_SMOKER, information.smoker!!)
            emit(Resource.Success(null))
        } catch (e: HttpException) {
            emit(Resource.Error("Registration failed: $e", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("Registration failed: $e", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("Registration failed: $e", code = 2))
        }

    }
}
