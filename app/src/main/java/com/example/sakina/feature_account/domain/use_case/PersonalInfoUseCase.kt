package com.example.sakina.feature_account.domain.use_case

import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_account.domain.model.ChangeNameRequest
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
            MySharedPref.putInt(Constant.Age,information.age)
            MySharedPref.putInt(Constant.Height,information.height)
            MySharedPref.putInt(Constant.Weight,information.weight)
            MySharedPref.putBool(Constant.Diabetic,information.diabetic)
            MySharedPref.putBool(Constant.Hypertension,information.hypertension)
            MySharedPref.putBool(Constant.Hypotension,information.hypotension)
            MySharedPref.putBool(Constant.Smoker,information.smoker)
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