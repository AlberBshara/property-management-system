package com.example.pms.viewmodel.api.estates_services


import com.example.pms.model.*
import com.example.pms.viewmodel.api.RetrofitClient
import com.example.pms.viewmodel.api.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException


class EstateServiceImplementation(
    private val estateServiceInterface: EstateServicesInterface = RetrofitClient.EstateRepository
) {

    suspend fun getEstateById(
        token: String, id: Int
    ): Flow<Resource<EstateViewMoreData>> {
        return flow {
            emit(Resource.Loading(true))
            val response = try {
                estateServiceInterface.getEstateById(token, id)
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.message.toString()))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(e.message.toString()))
                null
            }
            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))
        }
    }

    suspend fun postPublishNewEstate(
        token: String,
        estate: EstatePublishNewPostRequest,
        imageList: List<MultipartBody.Part>?
    ): Flow<Resource<EstatePublishNewPostRequest.EstatePublishNewPostResponse>> {

        return flow {
            emit(Resource.Loading(true))
            val response = try {
                estateServiceInterface.publishNewEstate(
                    authToken = token,
                    operationType = estate.operation_type.toRequestBody("text/plain".toMediaTypeOrNull()),
                    governorate = estate.governorate.toRequestBody("text/plain".toMediaTypeOrNull()),
                    description = estate.description.toRequestBody("text/plain".toMediaTypeOrNull()),
                    price = estate.price.toRequestBody("text/plain".toMediaTypeOrNull()),
                    address = estate.address.toRequestBody("text/plain".toMediaTypeOrNull()),
                    status = estate.status.toRequestBody("text/plain".toMediaTypeOrNull()),
                    estate_type = estate.estate_type.toRequestBody("text/plain".toMediaTypeOrNull()),
                    space = estate.space.toRequestBody("text/plain".toMediaTypeOrNull()),
                    beds = estate.beds.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    level = estate.level.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    baths = estate.baths.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                    garage = estate.garage.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull()),
                    locationInDamascus = estate.locationInDamascus.toRequestBody("text/plain".toMediaTypeOrNull()),
                    image1 = imageList?.let { if (it.isNotEmpty()) it[0] else null },
                    image2 = imageList?.let { if (it.size > 1) it[1] else null },
                    image3 = imageList?.let { if (it.size > 2) it[2] else null },
                    image4 = imageList?.let { if (it.size > 3) it[3] else null },
                    image5 = imageList?.let { if (it.size > 4) it[4] else null },
                    image6 = imageList?.let { if (it.size > 5) it[5] else null },
                    image7 = imageList?.let { if (it.size > 6) it[6] else null },
                    image8 = imageList?.let { if (it.size > 7) it[7] else null },
                    image9 = imageList?.let { if (it.size > 8) it[8] else null },
                    image10 = imageList?.let { if (it.size > 9) it[9] else null },
                )

            } catch (e: HttpException) {
                emit(Resource.Error(e.toString()))
                null
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.toString()))
                null
            }
            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))

            }
            emit(Resource.Loading(false))

        }
    }

    suspend fun getAllEstatesHome(
        token: String,
        pageNumber: Int
    ): Flow<Resource<GetAllEstateResponse>> {
        return flow {
            emit(Resource.Loading(true))
            val response = try {
                estateServiceInterface.getAllPostsEstates(token = token, pageNumber)
            } catch (e: HttpException) {
                emit(Resource.Error(e.toString()))
                null
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
                null
            }
            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))

        }
    }


    suspend fun getNumOfLikesEstate(
        token: String,
        idAndType: FavouriteEstates.GetNumberOfLikesRequest
    ): Flow<Resource<FavouriteEstates.GetNumberOfLikesResponse>> {
        return flow {

            emit(Resource.Loading(true))
            val response = try {
                estateServiceInterface.getNumOfLikesEstate(token = token, numOfLikes = idAndType)
            } catch (e: HttpException) {
                emit(Resource.Error(e.toString()))
                null
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.toString()))
                null
            }

            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))


        }
    }


    suspend fun addOrRemoveFromFavourites(
        token: String,
        favourite: FavouriteEstates.AddOrRemoveFromFavouritesRequest
    ): Flow<Resource<FavouriteEstates.AddOrRemoveFromFavouritesResponse>> {
        return flow {

            emit(Resource.Loading(true))
            val response = try {
                estateServiceInterface.addOrRemoveFromFavourites(
                    token = token,
                    favourite = favourite
                )
            } catch (e: HttpException) {
                emit(Resource.Error(e.toString()))
                null
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.toString()))
                null
            }

            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))

        }
    }


    suspend fun getRateEstate(
        token: String,
        rate: RateEstate.GetRateEstateRequest
    ): Flow<Resource<RateEstate.GetRateEstateResponse>> {
        return flow {

            emit(Resource.Loading(true))
            val response = try {
                estateServiceInterface.getRateEstate(token = token, rate = rate)
            } catch (e: HttpException) {
                emit(Resource.Error(e.toString()))
                null
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.toString()))
                null
            }

            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))

        }
    }


    suspend fun searchEstate(
        token: String,
        search: SearchFilterEstate.SearchEstateRequest
    ): Flow<Resource<SearchFilterEstate.SearchEstateResponse>> {
        return flow {

            emit(Resource.Loading(true))
            val response = try {
                estateServiceInterface.searchEstate(token = token, search = search)
            } catch (e: HttpException) {
                emit(Resource.Error(e.toString()))
                null
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.toString()))
                null
            }

            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))

        }
    }


    suspend fun addRateEstate(
        token: String,
        rate: RateEstate.AddRateEstateRequest
    ): Flow<Resource<RateEstate.AddRateEstateResponse>> {
        return flow {

            emit(Resource.Loading(true))
            val response = try {
                estateServiceInterface.addRateEstate(token = token, rate = rate)
            } catch (e: HttpException) {
                emit(Resource.Error(e.toString()))
                null
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.toString()))
                null
            }

            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))

        }
    }

    suspend fun filterEstate(
        token: String,
        filter: SearchFilterEstate.FilterEstateRequest
    ): Flow<Resource<SearchFilterEstate.FilterEstateResponse>> {

        return flow {
            emit(Resource.Loading(true))
            val response = try {
                estateServiceInterface.filterEstate(
                    token, filter
                )
            } catch (e: HttpException) {
                emit(Resource.Error(e.toString()))
                null
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.toString()))
                null
            }

            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))


        }
    }


}