package com.example.pms.viewmodel.api.estates_services

import com.example.pms.model.*
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface EstateServicesInterface {


    @GET(Urls.GET_ESTATE_BY_ID_END_POINT + "/{${Keys.ID}}")
    suspend fun getEstateById(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Path(Keys.ID) estateId: Int
    ): EstateViewMoreData

    @Multipart
    @POST(Urls.PUBLISH_NEW_ESTATE_END_POINT)
    suspend fun publishNewEstate(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Part(Keys.OPERATION_TYPE) operationType: RequestBody,
        @Part(Keys.GOVERNORATE) governorate: RequestBody,
        @Part(Keys.DESCRIPTION) description: RequestBody,
        @Part(Keys.PRICE) price: RequestBody,
        @Part(Keys.ADDRESS) address: RequestBody,
        @Part(Keys.ESTATE_STATUS) status: RequestBody,
        @Part(Keys.ESTATE_TYPE) estate_type: RequestBody,
        @Part(Keys.SPACE) space: RequestBody,
        @Part(Keys.BEDS) beds: RequestBody,
        @Part(Keys.LEVEL) level: RequestBody,
        @Part(Keys.BATHS) baths: RequestBody,
        @Part(Keys.GARAGE) garage: RequestBody,
        @Part(Keys.SECOND_LOCATION) locationInDamascus: RequestBody,
        @Part image1: MultipartBody.Part? = null,
        @Part image2: MultipartBody.Part? = null,
        @Part image3: MultipartBody.Part? = null,
        @Part image4: MultipartBody.Part? = null,
        @Part image5: MultipartBody.Part? = null,
        @Part image6: MultipartBody.Part? = null,
        @Part image7: MultipartBody.Part? = null,
        @Part image8: MultipartBody.Part? = null,
        @Part image9: MultipartBody.Part? = null,
        @Part image10: MultipartBody.Part? = null,
    ): EstatePublishNewPostRequest.EstatePublishNewPostResponse

    @GET(Urls.GET_ALL_ESTATE_HOME_END_POINT)
    suspend fun getAllPostsEstates(
        @Header(Keys.AUTHORIZATION) token: String,
        @Query(Keys.PAGE_QUERY) currentPageNumber: Int
    ): GetAllEstateResponse


    @POST(Urls.GET_Number_Likes_Estate)
    suspend fun getNumOfLikesEstate(
        @Header(Keys.AUTHORIZATION) token: String,
        @Body numOfLikes: FavouriteEstates.GetNumberOfLikesRequest
    ): FavouriteEstates.GetNumberOfLikesResponse

    @POST(Urls.ADD_AND_REMOVE_FROM_FAVOURITES)
    suspend fun addOrRemoveFromFavourites(
        @Header(Keys.AUTHORIZATION) token: String,
        @Body favourite: FavouriteEstates.AddOrRemoveFromFavouritesRequest
    ): FavouriteEstates.AddOrRemoveFromFavouritesResponse

    @POST(Urls.GET_RATE_ESTATE)
    suspend fun getRateEstate(
        @Header(Keys.AUTHORIZATION) token: String,
        @Body rate: RateEstate.GetRateEstateRequest
    ): RateEstate.GetRateEstateResponse

    @POST(Urls.SEARCH_ESTATE_END_POINT)
    suspend fun searchEstate(
        @Header(Keys.AUTHORIZATION) token: String,
        @Body search: SearchFilterEstate.SearchEstateRequest
    ): SearchFilterEstate.SearchEstateResponse


    @POST(Urls.ADD_RATE_ESTATE)
    suspend fun addRateEstate(
        @Header(Keys.AUTHORIZATION) token: String,
        @Body rate: RateEstate.AddRateEstateRequest
    ): RateEstate.AddRateEstateResponse


    @POST(Urls.FILTER_ESTATE_END_POINTS)
    suspend fun filterEstate(
        @Header(Keys.AUTHORIZATION) token: String,
        @Body filter: SearchFilterEstate.FilterEstateRequest
    ): SearchFilterEstate.FilterEstateResponse


}