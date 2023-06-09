package com.example.pms.viewmodel.api.util

object Urls {
    const val BASE_URL: String = "https://a3df-185-107-56-212.ngrok-free.app"

    const val REGISTER_END_POINT: String = "Register/SignUp"
    const val LOGIN_END_POINT: String = "Register/LogIn"
    const val LOGOUT_END_POINT : String = "Register/LogOut"
    const val SEND_EMAIL_FORGET_PASSWORD: String = "Register/user/password/email"
    const val SEND_CODE_FORGET_PASSWORD: String = "Register/user/password/code/check"
    const val SEND_RESET_FORGET_PASSWORD : String = "Register/user/password/reset"
    const val PROFILE_END_POINT : String = "PMS/profile"

    const val POST_VEHICLE_END_POINT : String = "PMS/Add_Cars"
    const val GET_CAR_BY_ID_END_POINT : String = "PMS/Get_Car"
}