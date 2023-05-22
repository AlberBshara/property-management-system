package com.example.pms.viewmodel.api

import com.example.pms.viewmodel.api.user_services.UserServicesInterface
import com.example.pms.viewmodel.api.util.Urls
import com.example.pms.viewmodel.api.vehicels_services.VehicleServicesInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val client by lazy {
        Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userRepository: UserServicesInterface by lazy {
        client.create(UserServicesInterface::class.java)
    }

    val vehicleRepository: VehicleServicesInterface by lazy {
        client.create(VehicleServicesInterface::class.java)
    }
}