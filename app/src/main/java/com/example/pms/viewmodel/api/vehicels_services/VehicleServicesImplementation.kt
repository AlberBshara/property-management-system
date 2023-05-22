package com.example.pms.viewmodel.api.vehicels_services

import com.example.pms.model.PublishVehicleData
import com.example.pms.viewmodel.api.RetrofitClient
import com.example.pms.viewmodel.api.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody

class VehicleServicesImplementation(
    private val vehicleRepository: VehicleServicesInterface = RetrofitClient.vehicleRepository
) {

    suspend fun publishingVehicle(
        vehicle: PublishVehicleData
    ): Flow<Resource<ResponseBody>> = flow {
         emit(Resource.Loading(true))
    }
}
