package com.example.pms.viewmodel.api.vehicels_services

import com.example.pms.model.PublishVehicleData
import com.example.pms.model.VehicleViewMoreData
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Urls
import com.example.pms.viewmodel.utils.TokenManager
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Path

interface VehicleServicesInterface {




    @POST(Urls.POST_VEHICLE_END_POINT)
    suspend fun publishingVehicle(
        @Header(Keys.AUTHORIZATION) authToken : String ,
        @Body vehicle : PublishVehicleData
    ) : PublishVehicleData.PublishVehicleResponse


    @GET(Urls.GET_CAR_BY_ID_END_POINT+"/{${Keys.ID}}")
    suspend fun getVehicleById(
        @Header(Keys.AUTHORIZATION) authToken: String ,
        @Path(Keys.ID)  carId : Int
    ) : VehicleViewMoreData




}