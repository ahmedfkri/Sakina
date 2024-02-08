package com.example.sakina.core.util

object Constant {
    const val ON_BOARDING = "finished"
    const val SIGNED_UP = "signed_up"
    const val LOGGED_IN = "logged_up"

    const val BASE_URL = " https://6206-197-43-6-200.ngrok-free.app/api/"

    //field Names
    const val FIRST_NAME_FIELD = "first name"
    const val LAST_NAME_FIELD = "last name"
    const val EMAIL_FIELD = "email"
    const val PASS_FIELD = "password"

    const val USER_EMAIL = "email"
    const val USER_PASS = "pass"
    const val JWT_TOKEN = "jwt_token"
    const val REFRESH_TOKEN = "refresh_token"

    //Server Errors
    const val EMAIL_OR_PASSWORD_IS_NOT_CORRECT = "Email or password is not correct"
    const val EMAIL_IS_NOT_CONFIRMED = "Email is not confirmed"


    //Errors
    const val FIRST_NAME_ERROR = "First name can't be empty"
    const val LAST_NAME_ERROR = "Last name can't be empty"
    const val EMPTY_EMAIL_ERROR = "Email can't be empty"
    const val WRONG_EMAIL_FORM_ERROR = "Please enter valid email"
    const val EMPTY_PASS_ERROR = "Password can't be empty"

    const val WRONG_PASS_FORM_ERROR =
        "Password must be at least 6 letters containing upper/lower case, numbers and special cases"

}