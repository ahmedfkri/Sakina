package com.example.sakina.core.util

object Constant {

    const val TAG = "MYTag"


    const val ON_BOARDING = "finished"
    const val SIGNED_UP = "signed_up"
    const val LOGGED_IN = "logged_up"

    const val MAIN_API_BASE_URL = " https://d96f-197-43-37-77.ngrok-free.app/api/"
    const val ADVICE_BASE_URL = " https://d96f-197-43-37-77.ngrok-free.app/api/advices/"

    const val MODELS_API_BASE_URL = "https://5d6e-107-178-214-84.ngrok-free.app/"


    const val REQUEST_CODE_PICK_IMAGE = 1000
    const val REQUEST_CODE_PERMISSION = 1001


    const val FIRST_NAME = "first name"
    const val LAST_NAME = "last name"
    const val CURRENT_PASSWORD = "old password"
    const val NEW_PASSWORD = "new password"
    const val USER_MESSAGE_TYPE = 1
    const val BOT_MESSAGE_TYPE = 2

    const val Height = "height"
    const val Weight = "weight"
    const val Age = "age"
    const val Diabetic = "diabetic"
    const val Hypertension = "hypertension"
    const val Hypotension = "hypotension"
    const val Smoker = "smoker"



    //field Names
    const val FIRST_NAME_FIELD = "first name"
    const val LAST_NAME_FIELD = "last name"
    const val EMAIL_FIELD = "email"
    const val PASS_FIELD = "password"

    const val USER_EMAIL = "email"
    const val USER_PASS = "pass"
    const val JWT_TOKEN = "jwt_token"
    const val REFRESH_TOKEN = "refresh_token"
    const val EXP_ON = "exp_on"

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