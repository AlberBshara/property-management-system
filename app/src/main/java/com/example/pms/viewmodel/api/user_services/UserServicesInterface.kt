package com.example.pms.viewmodel.api.user_services

import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserServicesInterface {

    @FormUrlEncoded
    @POST(Urls.REGISTER_URL)
    suspend fun postRegisterData(
       @Field(Keys.NAME) name : String ,
       @Field(Keys.EMAIL) email : String ,
       @Field(Keys.PASSWORD) password : String,
       @Field(Keys.PHONE_NUMBER) phone_number : String
    ) : Response




}