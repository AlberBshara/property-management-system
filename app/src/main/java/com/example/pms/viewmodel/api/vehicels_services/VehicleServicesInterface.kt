package com.example.pms.viewmodel.api.vehicels_services

import com.example.pms.model.PublishVehicleData
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface VehicleServicesInterface {

    @POST(Urls.POST_VEHICLE_END_POINT)
    suspend fun publishingVehicle(
        @Body vehicle : PublishVehicleData
    ) : ResponseBody


}