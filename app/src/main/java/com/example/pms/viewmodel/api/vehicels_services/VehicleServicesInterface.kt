package com.example.pms.viewmodel.api.vehicels_services

import com.example.pms.model.*
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface VehicleServicesInterface {

    @Multipart
    @POST(Urls.POST_VEHICLE_END_POINT)
    suspend fun publishingVehicle(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Part(Keys.OPERATION_TYPE) operationType: RequestBody,
        @Part(Keys.TRANSMISSION_TYPE) transmissionType: RequestBody,
        @Part(Keys.BRAND) brand: RequestBody,
        @Part(Keys.SECONDARY_BRAND) secondaryBrand: RequestBody,
        @Part(Keys.GOVERNORATE) governorate: RequestBody,
        @Part(Keys.SECOND_LOCATION) secondaryLocation: RequestBody,
        @Part(Keys.COLOR) color: RequestBody,
        @Part(Keys.DESCRIPTION) description: RequestBody,
        @Part(Keys.PRICE) price: RequestBody,
        @Part(Keys.YEAR) yearOfManufacture: RequestBody,
        @Part(Keys.KILOMETERS) kilometers: RequestBody,
        @Part(Keys.ADDRESS) address: RequestBody,
        @Part(Keys.FUEL_TYPE) fuelType: RequestBody,
        @Part(Keys.CONDITION) condition: RequestBody,
        @Part(Keys.DRIVING_FORCE) drivingForce: RequestBody,
        @Part image: MultipartBody.Part? = null,
        @Part image1: MultipartBody.Part? = null,
        @Part image2: MultipartBody.Part? = null,
        @Part image3: MultipartBody.Part? = null,
        @Part image4: MultipartBody.Part? = null,
        @Part image5: MultipartBody.Part? = null,
        @Part image6: MultipartBody.Part? = null,
        @Part image7: MultipartBody.Part? = null,
        @Part image8: MultipartBody.Part? = null,
        @Part image9: MultipartBody.Part? = null,
    ): PublishVehicleData.PublishVehicleResponse


    @GET(Urls.GET_CAR_BY_ID_END_POINT + "/{${Keys.ID}}")
    suspend fun getVehicleById(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Path(Keys.ID) carId: Int
    ): VehicleViewMoreData


    @GET(Urls.GET_ALL_VEHICLE)
    suspend fun getAllVehiclesPosts(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Query(Keys.PAGE_QUERY) currentPageNumber: Int
    ): HomeVehiclesResponse

    @POST(Urls.SEARCH_EDN_POINT)
    suspend fun search(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Body search: SearchData
    ): SearchData.SearchResponse


    @POST(Urls.FILTERING_END_POINT)
    suspend fun filter(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Body filteredData: FilteringData
    ): FilteringData.FilteringResponse

    @POST(Urls.LIKES_NUM_END_POINT)
    suspend fun likesNumById(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Body likes: LikesData
    ): LikesData.LikesNumResponse


    @POST(Urls.GET_RATE_EDN_POINT)
    suspend fun rateResult(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Body rateData: RateVehicleData
    ): RateVehicleData.RateVehicleResponse

    @POST(Urls.ADD_VEHICLE_RATE_END_POINT)
    suspend fun ratingVehicle(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Body ratingData: AddVehicleRateData
    ): AddVehicleRateData.RatingVehicleResponse


}