package com.example.pms.viewmodel.api.vehicels_services

import com.example.pms.model.*
import com.example.pms.viewmodel.api.RetrofitClient
import com.example.pms.viewmodel.api.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

class VehicleServicesImplementation(
    private val vehicleServicesInterface: VehicleServicesInterface = RetrofitClient.vehicleRepository
) {

    suspend fun publishingVehicle(
        vehicle: PublishVehicleData,
        imagesList: List<MultipartBody.Part>?,
        token: String,
    ): Flow<Resource<PublishVehicleData.PublishVehicleResponse>> = flow {
        emit(Resource.Loading(true))
        val response = try {
            vehicleServicesInterface.publishingVehicle(
                authToken = token,
                operationType = vehicle.operationType.toRequestBody("text/plain".toMediaTypeOrNull()),
                transmissionType = vehicle.transmissionType.toRequestBody("text/plain".toMediaTypeOrNull()),
                brand = vehicle.brand.toRequestBody("text/plain".toMediaTypeOrNull()),
                secondaryBrand = vehicle.secondaryBrand.toRequestBody("text/plain".toMediaTypeOrNull()),
                governorate = vehicle.governorate.toRequestBody("text/plain".toMediaTypeOrNull()),
                secondaryLocation = vehicle.locationInDamascus.toRequestBody("text/plain".toMediaTypeOrNull()),
                color = vehicle.color.toRequestBody("text/plain".toMediaTypeOrNull()),
                description = vehicle.description.toRequestBody("text/plain".toMediaTypeOrNull()),
                price = vehicle.price.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                yearOfManufacture = vehicle.yearOfManufacture.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull()),
                kilometers = vehicle.kilometers.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull()),
                address = vehicle.address.toRequestBody("text/plain".toMediaTypeOrNull()),
                fuelType = vehicle.fuelType.toRequestBody("text/plain".toMediaTypeOrNull()),
                condition = vehicle.condition.toRequestBody("text/plain".toMediaTypeOrNull()),
                drivingForce = vehicle.drivingForce.toRequestBody("text/plain".toMediaTypeOrNull()),
                image  = imagesList?.let { if (it.size > 0) it[0] else null },
                image1 = imagesList?.let { if (it.size > 1) it[1] else null },
                image2 = imagesList?.let { if (it.size > 2) it[2] else null },
                image3 = imagesList?.let { if (it.size > 3) it[3] else null },
                image4 = imagesList?.let { if (it.size > 4) it[4] else null },
                image5 = imagesList?.let { if (it.size > 5) it[5] else null },
                image6 = imagesList?.let { if (it.size > 6) it[6] else null },
                image7 = imagesList?.let { if (it.size > 7) it[7] else null },
                image8 = imagesList?.let { if (it.size > 8) it[8] else null },
                image9 = imagesList?.let { if (it.size > 9) it[9] else null },
            )
        } catch (e: IOException) {
            emit(Resource.Error(e.cause.toString()))
            null
        } catch (e: HttpException) {
            emit(Resource.Error(e.cause.toString()))
            null
        }
        response?.let {
            emit(Resource.Success(data = response))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }


    suspend fun getVehicleById(
        token: String, carId: Int
    ): Flow<Resource<VehicleViewMoreData>> = flow {
        emit(Resource.Loading(true))
        val response = try {
            vehicleServicesInterface.getVehicleById(token, carId)
        } catch (e: IOException) {
            emit(Resource.Error(message = e.toString()))
            null
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response().toString()))
            null
        }
        response?.let {
            emit(Resource.Success(data = response))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }


    suspend fun fetchAllVehiclesPosts(
        token: String,
        pageNumber: Int
    ): Flow<Resource<HomeVehiclesResponse>> = flow {
        emit(Resource.Loading(true))
        val response = try {
            vehicleServicesInterface.getAllVehiclesPosts(
                token, pageNumber
            )
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
            null
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response().toString()))
            null
        }
        response?.let {
            emit(Resource.Success(data = it))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }

    suspend fun search(
        token: String,
        searchData: SearchData
    ): Flow<Resource<SearchData.SearchResponse>> = flow {
        emit(Resource.Loading(true))
        val response = try {
            vehicleServicesInterface.search(token, searchData)
        } catch (e: IOException) {
            emit(Resource.Error(message = e.toString()))
            null
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response().toString()))
            null
        }
        response?.let {
            emit(Resource.Success(data = it))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }


    suspend fun filter(
        token: String,
        filteredData: FilteringData
    ): Flow<Resource<FilteringData.FilteringResponse>> = flow {
        emit(Resource.Loading(true))
        val response = try {
            vehicleServicesInterface.filter(token, filteredData)
        } catch (e: IOException) {
            emit(Resource.Error(message = e.toString()))
            null
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response().toString()))
            null
        }
        response?.let {
            emit(Resource.Success(data = it))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }


    suspend fun likesNumById(
        token : String ,
        likes : LikesData
    ) : Flow<Resource<LikesData.LikesNumResponse>> = flow {
        emit(Resource.Loading(true))
        val response = try {
            vehicleServicesInterface.likesNumById(
                token , likes
            )
        }catch (e : IOException) {
            emit(Resource.Error(message = e.toString()))
            null
        }catch (e : HttpException) {
            emit(Resource.Error(message = e.response().toString()))
            null
        }
        response?.let {
            emit(Resource.Success(data = it))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }


}
