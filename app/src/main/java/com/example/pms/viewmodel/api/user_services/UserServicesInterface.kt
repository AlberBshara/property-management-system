package com.example.pms.viewmodel.api.user_services

import com.example.pms.model.LoginUserRequest
import com.example.pms.model.RegisterUserData
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST


interface UserServicesInterface {


    @POST(Urls.REGISTER_END_POINT)
    suspend fun postRegisterData(
        @Body user: RegisterUserData
    ): ResponseBody

    @POST(Urls.LOGIN_END_POINT)
    suspend fun postLoginData(
        @Body user : LoginUserRequest
    ): LoginUserRequest.LoginResponse


}