package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.api.vehicels_services.VehicleServicesImplementation
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch
import com.example.pms.R

class VehicleDetailsScreenVM(
    private val vehicleApiImp: VehicleServicesImplementation = VehicleServicesImplementation(),
) : ViewModel() {

    var state by mutableStateOf(VehicleDetailsState())

    companion object {
        private const val TAG: String = "VehicleDetailsScreenVM.kt"
    }

    private var counter: Int = 0

    fun onEvent(event: VehicleDetailsEvents) {
        when (event) {
            is VehicleDetailsEvents.OnStart -> {
                getVehicleDataById(event.context, event.carId)
            }
            is VehicleDetailsEvents.OnCurrentImageIndexChanged -> {
                state = state.copy(
                    currentImageIndex = event.index
                )
            }
            is VehicleDetailsEvents.OnCallPhoneClicked -> {
                makingCall(event.phoneNumber, event.context)
            }
            is VehicleDetailsEvents.OnShareClicked -> {
                shareVehicleData(event.context)
            }
            is VehicleDetailsEvents.OnReloadClicked -> {
                reload(event.context, event.carId)
            }
        }
    }


    @SuppressLint("LongLogTag")
    private fun getVehicleDataById(
        context: Context,
        carId: Int
    ) {
        if (counter == 0) {
            counter++
            state = state.copy(
                isLoading = true
            )
            viewModelScope.launch {
                val response = vehicleApiImp.getVehicleById(
                    TokenManager.getInstance(context).getToken(),
                    carId
                )
                response.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                            Log.d(TAG, "getVehicleDataById: Loading: ${it.isLoading}")
                        }
                        is Resource.Success -> {
                            Log.d(TAG, "getVehicleDataById: Success: ${it.data.toString()}")
                            if (it.data != null) {
                                if (it.data.status) {
                                    val vehicle = it.data.vehicle
                                    val owner = it.data.owner
                                    state = state.copy(
                                        ownerId = vehicle.ownerId,
                                        brand = vehicle.brand,
                                        secondBrand = vehicle.secondaryBrand
                                            ?: context.getString(R.string.invalid),
                                        operationType = vehicle.operationType,
                                        transmissionType = vehicle.transmissionType,
                                        color = vehicle.color
                                            ?: context.getString(R.string.invalid),
                                        fuelType = vehicle.fuelType,
                                        drivingForce = vehicle.drivingForce,
                                        yearOfManufacture = vehicle.yearOfManufacture,
                                        kilometer = vehicle.kilometers,
                                        condition = vehicle.condition,
                                        price = vehicle.price,
                                        description = vehicle.description,
                                        location = vehicle.governorate + ", " + (vehicle.address
                                            ?: context.getString(R.string.invalid)),
                                        userId = owner.id ,
                                        userName = owner.userName,
                                        email = owner.email,
                                        phoneNumber = owner.phoneNumber,
                                        userImage = owner.image ,
                                    )
                                    try {
                                        if (it.data.imagesList.isNotEmpty()) {
                                            state = state.copy(
                                                imagesList = it.data.imagesList.toMutableList()
                                            )
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }
                        is Resource.Error -> {
                            Log.d(TAG, "getVehicleDataById: ${it.message}")
                                state = state.copy(
                                    timeOut = true
                                )
                        }
                    }
                }
            }
        }

    }

    private fun reload(
        context: Context, carId: Int
    ) {
        this.counter = 0
        getVehicleDataById(context, carId)
        state = state.copy(
            timeOut = false
        )
    }

    private fun makingCall(phoneNumber: String, context: Context) {
        viewModelScope.launch {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:$phoneNumber")
            context.startActivity(dialIntent)
        }
    }

    private fun shareVehicleData(context: Context) {
        viewModelScope.launch {
            val vehicleData = "write here all the data of the current vehicle"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, vehicleData)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }
}