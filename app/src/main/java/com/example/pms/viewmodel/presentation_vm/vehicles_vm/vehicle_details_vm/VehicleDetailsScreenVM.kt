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
import com.example.pms.model.LikeData
import com.example.pms.model.LikesData
import com.example.pms.viewmodel.api.user_services.UserServicesRepository


class VehicleDetailsScreenVM(
    private val vehicleApiImp: VehicleServicesImplementation = VehicleServicesImplementation(),
    private val userServicesRepository: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    var state: VehicleDetailsState by mutableStateOf(VehicleDetailsState())

    companion object {
        private const val TAG: String = "VehicleDetailsScreenVM.kt"
        private const val TYPE: String = "car"
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
            is VehicleDetailsEvents.LikeClicked -> {
                like(event.carId, event.context)
            }
            is VehicleDetailsEvents.OnRefresh -> {
                reload(event.context, event.carId)
            }
        }
    }


    @SuppressLint("LongLogTag", "SuspiciousIndentation")
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
                val token = TokenManager.getInstance(context).getToken()
                val response = vehicleApiImp.getVehicleById(
                    token, carId
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

                                    var likesNumber = 0
                                    vehicleApiImp.likesNumById(
                                        token, LikesData(carId, TYPE)
                                    ).collect { likesResponse ->
                                        when (likesResponse) {
                                            is Resource.Loading -> {}
                                            is Resource.Success -> {
                                                likesResponse.data?.let { likeResult ->
                                                    likesNumber = likeResult.likesNumber
                                                    Log.d(TAG, "likes Number $likesNumber")
                                                }
                                            }
                                            is Resource.Error -> {}
                                        }
                                    }

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
                                        userId = owner.id,
                                        userName = owner.userName,
                                        email = owner.email,
                                        phoneNumber = owner.phoneNumber,
                                        userImage = owner.image,
                                        likesNumber = likesNumber
                                    )
                                    if (it.data.imagesList.isNotEmpty()) {
                                        state = state.copy(
                                            imagesList = it.data.imagesList.toMutableList()
                                        )
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
            val vehicleData = "${context.getString(R.string.vehicle_info)} \n" +
                    "${context.getString(R.string.brand)} : ${state.brand}\n" +
                    "${context.getString(R.string.price)} : ${state.price}\n" +
                    "${context.getString(R.string.condition)} : ${state.condition}\n" +
                    "${context.getString(R.string.color)} : ${state.color}\n" +
                    "${context.getString(R.string.location)} : ${state.location}\n" +
                    "${context.getString(R.string.fuel_type)} : ${state.fuelType}\n" +
                    "${context.getString(R.string.deriving_force)} : ${state.drivingForce}\n" +
                    "${context.getString(R.string.transmission_type)} : ${state.transmissionType}\n" +
                    "${context.getString(R.string.operation)} : ${state.operationType}\n" +
                    "${context.getString(R.string.manufacture_year)} : ${state.yearOfManufacture}\n" +
                    "${context.getString(R.string.owner)} : ${state.userName}\n" +
                    "${context.getString(R.string.description)} : ${state.description}\n" +
                    "${context.getString(R.string.phone)} : ${state.phoneNumber}"

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, vehicleData)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }

    @SuppressLint("LongLogTag")
    private fun like(
        carId: Int, context: Context
    ) {
        state = state.copy(
            isLiked = true
        )
        viewModelScope.launch {
            val response = userServicesRepository.like(
                TokenManager.getInstance(context).getToken(),
                LikeData(carId, TYPE)
            )
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        it.data?.let { likedResponse ->
                            if (likedResponse.success) {
                                if (likedResponse.message.contains("un")) {
                                    state = state.copy(
                                        isLiked = false
                                    )
                                } else {
                                    state = state.copy(
                                        isLiked = true
                                    )
                                }
                                likesNumber(context , carId ,
                                onSuccessListener = { likesNumRes ->
                                    state = state.copy(
                                        likesNumber = likesNumRes
                                    )
                                })
                                Log.d(TAG, " like : ${likedResponse.message}")
                            }
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLiked = false
                        )
                    }
                }
            }
        }
    }

    private fun likesNumber(
        context: Context,
        carId: Int,
        onSuccessListener: (Int) -> Unit
    ) {
        viewModelScope.launch {
            vehicleApiImp.likesNumById(
                TokenManager.getInstance(context).getToken(),
                LikesData(carId, TYPE)
            ).collect { likesResponse ->
                when (likesResponse) {
                    is Resource.Success -> {
                        likesResponse.data?.let { likeResult ->
                            onSuccessListener(likeResult.likesNumber)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

}