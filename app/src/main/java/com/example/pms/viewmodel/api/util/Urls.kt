package com.example.pms.viewmodel.api.util

object Urls {
    const val BASE_URL: String = "http://192.168.43.160:8000"

    const val REGISTER_END_POINT: String = "Register/SignUp"
    const val LOGIN_END_POINT: String = "Register/LogIn"
    const val LOGOUT_END_POINT : String = "Register/LogOut"
    const val SEND_EMAIL_FORGET_PASSWORD: String = "Register/user/password/email"
    const val SEND_CODE_FORGET_PASSWORD: String = "Register/user/password/code/check"
    const val SEND_RESET_FORGET_PASSWORD : String = "Register/user/password/reset"
    const val PROFILE_END_POINT : String = "PMS/profile"
    const val EDIT_USER_PROFILE_END_POINT : String = "PMS/UpdateProfile"
    const val GET_USER_PROFILE_END_POINT : String = "PMS/profile"
    const val EDIT_USER_IMAGE_END_POINT :String="PMS/Insert_Image"
    const val RESET_PASSWORD_WITH_TOKEN_END_POINT:String="PMS/reset_password"
    const val MY_FAV_LIST_END_POINT : String = "PMS/Get_All_Favorite"
    const val LIKE_END_POINT : String = "PMS/Add_To_Favorite"

    const val POST_VEHICLE_END_POINT : String = "PMS/Add_Cars"
    const val GET_CAR_BY_ID_END_POINT : String = "PMS/Get_Car"
    const val GET_ALL_VEHICLE : String = "PMS/Car_Home/"
    const val SEARCH_EDN_POINT : String = "PMS/search"
    const val FILTERING_END_POINT : String = "PMS/filter"
    const val LIKES_NUM_END_POINT : String = "PMS/Likes_number"




}