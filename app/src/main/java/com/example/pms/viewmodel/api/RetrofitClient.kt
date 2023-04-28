package com.example.pms.viewmodel.api

import com.example.pms.viewmodel.api.user_services.UserServicesInterface
import com.example.pms.viewmodel.api.util.Urls
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private val moshiConverter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

     private val client = Retrofit.Builder()
        .baseUrl(Urls.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
        .build()

    val userRepository : UserServicesInterface = client.create(UserServicesInterface::class.java)
}