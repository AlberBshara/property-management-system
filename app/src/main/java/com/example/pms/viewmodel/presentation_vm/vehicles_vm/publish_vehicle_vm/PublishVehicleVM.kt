package com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pms.model.PublishVehicleData
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.api.vehicels_services.VehicleServicesImplementation
import com.example.pms.viewmodel.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class PublishVehicleVM(
    private val vehicleApiRepo: VehicleServicesImplementation = VehicleServicesImplementation()
) : ViewModel() {

    var state by mutableStateOf(PublishVehicleState())

    companion object {
        private const val MAX_IMAGES: Int = 10
        private const val TAG: String = "PublishVehicleVM.kt"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: PublishVehicleEvents) {
        when (event) {
            is PublishVehicleEvents.AddNewImage -> {
                updateImagesList(event.updatedImagesList, event.context)
            }
            is PublishVehicleEvents.OnRemoveImage -> {
                removeImage(event.id)
            }
            is PublishVehicleEvents.ImageIndexChanged -> {
                state = state.copy(
                    indexOfCurrentImage = event.id
                )
            }
            is PublishVehicleEvents.ImageCollapsing -> {
                collapsingImageCalculation(
                    event.lazyListState,
                    event.graphicsLayer
                )
            }
            is PublishVehicleEvents.OnBrandChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        brand = event.brand
                    )
                )
            }
            is PublishVehicleEvents.OnListingTypeChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        listingType = event.listingType
                    )
                )
            }
            is PublishVehicleEvents.OnTransmissionChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        transmissionType = event.transmissionType
                    )
                )
            }
            is PublishVehicleEvents.OnSecondaryBrandChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        secondaryBrand = event.secondaryBrand
                    )
                )
            }
            is PublishVehicleEvents.OnPriceChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        price = event.price
                    )
                )
            }
            is PublishVehicleEvents.OnKiloMeterChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        kilometer = event.kilometer * 100000
                    )
                )
            }
            is PublishVehicleEvents.OnGovernorateChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        governorate = event.governorate
                    )
                )
            }
            is PublishVehicleEvents.OnLocationChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        location = event.location
                    )
                )
            }
            is PublishVehicleEvents.OnManufactureYearChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        manufactureYear = event.manufactureYear
                    )
                )
            }
            is PublishVehicleEvents.OnConditionChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        condition = event.condition
                    )
                )
            }
            is PublishVehicleEvents.ShowYearDialogPicker -> {
                state = state.copy(
                    showYearDialogPicker = !state.showYearDialogPicker
                )
            }
            is PublishVehicleEvents.OnDescriptionChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        description = event.description
                    )
                )
            }
            is PublishVehicleEvents.OnColorChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        color = event.color
                    )
                )
            }
            is PublishVehicleEvents.Submit -> {
                submitData(event.context)
            }
            is PublishVehicleEvents.OnFuelTypeChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        fuelType = event.fuelType
                    )
                )
            }
            is PublishVehicleEvents.ImageDetectionCautionOk -> {
                deleteTheFakeImage(event.indicesList)
            }
            is PublishVehicleEvents.OnDerivingForceChanged -> {
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        derivingForce = event.derivingForce
                    )
                )
            }
            is PublishVehicleEvents.ShowLocationPermission -> {
                state = state.copy(
                    showLocationPermission = !state.showLocationPermission
                )
            }
            is PublishVehicleEvents.GetLocation -> {
                getLocation(event.context)
            }
            is PublishVehicleEvents.WifiCase.Confirm -> {
                state = state.copy(
                    requestInternetPermission = !state.requestInternetPermission
                )
                InternetConnection.turnOnWifi(event.context)
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is PublishVehicleEvents.WifiCase.Deny -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is PublishVehicleEvents.HideSnackBar -> {
                state = state.copy(
                    dataInvalid = false
                )
            }
            is PublishVehicleEvents.OnDoneClicked -> {
                state = state.copy(
                    done = false
                )
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun submitData(context: Context) {
        if (state.enteredData.isValid()
            && state.enteredData.listOfSelectedImages.isNotEmpty()
        ) {


            val cd = state.enteredData
            val vehicleData = PublishVehicleData(
                operationType = cd.listingType,
                transmissionType = cd.transmissionType,
                brand = cd.brand,
                secondaryBrand = cd.secondaryBrand,
                governorate = cd.governorate,
                locationInDamascus = "Barzeh",
                color = cd.color,
                description = cd.description,
                price = cd.price,
                yearOfManufacture = cd.manufactureYear.toInt(),
                kilometers = cd.kilometer,
                address = cd.location,
                condition = cd.condition,
                fuelType = cd.fuelType,
                drivingForce = cd.derivingForce
            )


            InternetConnection.run(context,
                connected = {
                    viewModelScope.launch {

                        val image1 =
                            ImageHelper.uriToMultipart(context, cd.listOfSelectedImages[0], "image")
                        val listOfImagesFiles: MutableList<MultipartBody.Part> =
                            mutableListOf(image1)
                        for (i in 1 until state.enteredData.listOfSelectedImages.size) {
                            val imagePart = ImageHelper.uriToMultipart(
                                context, cd.listOfSelectedImages[i], "image$i")
                            listOfImagesFiles.add(imagePart)
                        }
                        val response = vehicleApiRepo.publishingVehicle(
                            vehicle = vehicleData,
                            imagesList = listOfImagesFiles,
                            token = TokenManager.getInstance(context).getToken()
                        )
                        response.collect {
                            when (it) {
                                is Resource.Loading -> {
                                    Log.d(TAG, "submitData: ${it.isLoading}")
                                    state = state.copy(
                                        showUploadingAllData = it.isLoading
                                    )
                                }
                                is Resource.Success -> {
                                    if (it.data != null) {
                                        if (it.data.status) {
                                            Log.d(
                                                TAG,
                                                "submitData: Success ${it.data}"
                                            )
                                            state = state.copy(
                                                done = true
                                            )
                                        } else {
                                            Log.d(
                                                TAG,
                                                "submitData: Success with false status: ${it.data}"
                                            )
                                        }
                                    } else {
                                        Log.d(TAG, "submitData: Success but with null response")
                                    }
                                }
                                is Resource.Error -> {
                                    Log.d(TAG, "submitData: exception ${it.data}")
                                }
                            }
                        }
                    }
                },
                unconnected = {
                    state = state.copy(
                        showInternetAlert = true
                    )
                })
        } else {
            state = state.copy(
                dataInvalid = true
            )
        }
    }


    private fun updateImagesList(
        updatedImagesList: List<Uri>,
        context: Context
    ) {
        if (state.enteredData.listOfSelectedImages.size <= MAX_IMAGES) {
            val newImagesList = state.enteredData.listOfSelectedImages
                .toMutableList()
            viewModelScope.launch {
                newImagesList += updatedImagesList
                state = state.copy(
                    enteredData = state.enteredData.copy(
                        listOfSelectedImages = newImagesList
                    )
                )
            }
            detectVehicles(newImagesList, context)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun detectVehicles(
        imageList: List<Uri>,
        context: Context
    ) {
        viewModelScope.launch {
            ImageDetection.detectVehicleInImage(imageList, context,
                onResult = { result ->
                    Log.d("Image Detection", "detectVehicles: indices list:  $result")
                    /**
                     * result contains the indices of each image is not a vehicle.
                     * when update the list of imageDetectionCaution will update the state.
                     */
                    state = state.copy(
                        imageDetectionCaution = result
                    )
                }
            )
        }
    }

    private fun deleteTheFakeImage(indicesList: List<Int>) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                indicesList.reversed().forEach {
                    val newImagesList = state.enteredData.listOfSelectedImages
                        .toMutableList()
                    newImagesList.removeAt(it)
                    state = state.copy(
                        enteredData = state.enteredData.copy(
                            listOfSelectedImages = newImagesList.distinct()
                        )
                    )
                }
                state = state.copy(
                    imageDetectionCaution = emptyList()
                )
            }
        }
    }

    private fun removeImage(id: Int) {
        val newImagesList = state.enteredData.listOfSelectedImages
            .toMutableList()
        viewModelScope.launch {
            newImagesList.removeAt(id)
            state = state.copy(
                enteredData = state.enteredData.copy(
                    listOfSelectedImages = newImagesList.distinct()
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getLocation(context: Context) {
        viewModelScope.launch {
            InternetConnection.run(
                context,
                connected = {
                    state = state.copy(
                        showIndicator = true
                    )
                    launch {
                        LocationHelper.getCurrentLocation(context,
                            onLocationListener = { result ->
                                state = state.copy(
                                    enteredData = state.enteredData.copy(
                                        location = result.address
                                    ),
                                    showLocationPermission = false,
                                    showIndicator = false
                                )
                            })
                    }
                },
                unconnected = {
                    state = state.copy(
                        showInternetAlert = true
                    )
                }
            )
        }
    }

    private fun collapsingImageCalculation(
        lazyListState: LazyListState,
        graphicsLayer: GraphicsLayerScope
    ) {
        viewModelScope.launch {
            state.scrolledY += lazyListState.firstVisibleItemScrollOffset - state.previousOffset
            graphicsLayer.translationY = state.scrolledY * 0.5f
            state.previousOffset = lazyListState.firstVisibleItemScrollOffset
        }
    }
}