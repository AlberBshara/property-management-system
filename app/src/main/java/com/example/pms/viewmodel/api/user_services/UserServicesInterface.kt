package com.example.pms.viewmodel.api.user_services

import com.example.pms.model.LoginUserRequest
import com.example.pms.model.LogoutResponse
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface UserServicesInterface {



    @Multipart
    @POST(Urls.REGISTER_END_POINT)
    suspend fun postRegisterData(
        @Part(Keys.NAME) name : RequestBody ,
        @Part(Keys.EMAIL) email : RequestBody ,
        @Part(Keys.PASSWORD) password : RequestBody ,
        @Part(Keys.PHONE_NUMBER) phoneNumber : RequestBody ,
        @Part image : MultipartBody.Part? = null
    ): ResponseBody

    @POST(Urls.LOGIN_END_POINT)
    suspend fun postLoginData(
        @Body user: LoginUserRequest
    ): LoginUserRequest.LoginResponse

    @GET(Urls.LOGOUT_END_POINT)
    suspend fun logOut(
        @Header(Keys.AUTHORIZATION) token: String
    ) : LogoutResponse



}