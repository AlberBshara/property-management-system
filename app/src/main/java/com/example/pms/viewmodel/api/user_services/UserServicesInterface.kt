package com.example.pms.viewmodel.api.user_services

import com.example.pms.model.RegisterUserData
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST


interface UserServicesInterface {



    @Multipart
    @POST(Urls.REGISTER_END_POINT)
    suspend fun postRegisterData(
        @Body user: RegisterUserData
    ): ResponseBody






}