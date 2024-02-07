package com.example.sakina.feature_authentication.domain.use_case

import com.example.sakina.core.util.Constant
import com.example.sakina.feature_authentication.domain.model.RegisterRequest

class ValidateSignUpUseCase {


    operator fun invoke(registerRequest: RegisterRequest): Map<String,String?> {

        return mapOf(
            Constant.FIRST_NAME_FIELD to validateFirstName(registerRequest.firstName),
            Constant.LAST_NAME_FIELD to validateFirstName(registerRequest.lastName),
            Constant.EMAIL_FIELD to validateEmail(registerRequest.email),
            Constant.PASS_FIELD to validatePassword(registerRequest.password)
        )


    }


    private fun validateFirstName(firstName: String): String? {
        return if (firstName.isEmpty()) Constant.FIRST_NAME_ERROR else null
    }
    private fun validateLastName(lastName: String): String? {
        return if (lastName.isEmpty()) Constant.LAST_NAME_ERROR else null
    }

    private fun validateEmail(email: String): String? {
        return if (email.isEmpty()) Constant.EMPTY_EMAIL_ERROR
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) Constant.WRONG_EMAIL_FORM_ERROR
        else null
    }

    private fun validatePassword(password: String): String? {
        return if (password.isEmpty()) Constant.EMPTY_PASS_ERROR
        else if (!isPasswordValid(password)) Constant.WRONG_PASS_FORM_ERROR
        else null
    }


    private fun isPasswordValid(password: String): Boolean {
        val pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$"
        return password.matches(pattern.toRegex())
    }


}