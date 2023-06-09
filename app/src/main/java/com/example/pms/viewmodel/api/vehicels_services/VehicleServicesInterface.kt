package com.example.pms.viewmodel.api.vehicels_services

import com.example.pms.model.PublishVehicleData
import com.example.pms.model.VehicleViewMoreData
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface VehicleServicesInterface {

    @Multipart
    @POST(Urls.POST_VEHICLE_END_POINT)
    suspend fun publishingVehicle(
        @Header(Keys.AUTHORIZATION) authToken : String ,
        @Part(Keys.OPERATION_TYPE) operationType : RequestBody ,
        @Part(Keys.TRANSMISSION_TYPE) transmissionType : RequestBody ,
        @Part(Keys.BRAND) brand : RequestBody ,
        @Part(Keys.SECONDARY_BRAND) secondaryBrand : RequestBody ,
        @Part(Keys.GOVERNORATE) governorate : RequestBody ,
        @Part(Keys.SECOND_LOCATION) secondaryLocation : RequestBody ,
        @Part(Keys.COLOR) color : RequestBody ,
        @Part(Keys.DESCRIPTION) description : RequestBody ,
        @Part(Keys.PRICE) price : RequestBody ,
        @Part(Keys.YEAR) yearOfManufacture : RequestBody ,
        @Part(Keys.KILOMETERS) kilometers : RequestBody ,
        @Part(Keys.ADDRESS)  address : RequestBody ,
        @Part(Keys.FUEL_TYPE) fuelType : RequestBody ,
        @Part(Keys.CONDITION) condition : RequestBody ,
        @Part(Keys.DRIVING_FORCE) drivingForce : RequestBody ,
        @Part image1 : MultipartBody.Part? = null,
        @Part image2 : MultipartBody.Part? = null,
        @Part image3 : MultipartBody.Part? = null,
        @Part image4 : MultipartBody.Part? = null,
        @Part image5 : MultipartBody.Part? = null,
        @Part image6 : MultipartBody.Part? = null,
        @Part image7 : MultipartBody.Part? = null,
        @Part image8 : MultipartBody.Part? = null,
        @Part image9 : MultipartBody.Part? = null,
        @Part image10 : MultipartBody.Part? = null,
    ) : PublishVehicleData.PublishVehicleResponse


    @GET(Urls.GET_CAR_BY_ID_END_POINT+"/{${Keys.ID}}")
    suspend fun getVehicleById(
        @Header(Keys.AUTHORIZATION) authToken: String ,
        @Path(Keys.ID)  carId : Int
    ) : VehicleViewMoreData




}