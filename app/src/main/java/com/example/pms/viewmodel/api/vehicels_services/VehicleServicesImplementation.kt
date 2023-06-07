package com.example.pms.viewmodel.api.vehicels_services

import com.example.pms.model.PublishVehicleData
import com.example.pms.model.VehicleViewMoreData
import com.example.pms.viewmodel.api.RetrofitClient
import com.example.pms.viewmodel.api.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class VehicleServicesImplementation(
    private val vehicleServicesInterface: VehicleServicesInterface = RetrofitClient.vehicleRepository
) {

    suspend fun publishingVehicle(
        vehicle: PublishVehicleData,
        token: String,
    ): Flow<Resource<PublishVehicleData.PublishVehicleResponse>> = flow {
        emit(Resource.Loading(true))
        val response = try {
            vehicleServicesInterface.publishingVehicle(token, vehicle)
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
}
