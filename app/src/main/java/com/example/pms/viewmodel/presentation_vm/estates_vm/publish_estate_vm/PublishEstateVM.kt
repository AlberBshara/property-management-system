package com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pms.model.EstatePublishNewPostRequest
import com.example.pms.viewmodel.api.estates_services.EstateServiceImplementation
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.utils.ImageDetectionForEstates
import com.example.pms.viewmodel.utils.ImageHelper
import com.example.pms.viewmodel.utils.InternetConnection
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class PublishEstateVM(
   private val estateApiRepo: EstateServiceImplementation = EstateServiceImplementation()
) : ViewModel() {


    var state by mutableStateOf(PublishEstateState())


    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: PublishEstateEvents) {

        when (event) {

            is PublishEstateEvents.OnGovernorateTypeChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(governorate = event.governorate))
            }

            is PublishEstateEvents.OnAddressChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(address = event.address))
            }


            is PublishEstateEvents.OnEstateTypeChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(estateType = event.estateType))
            }

            is PublishEstateEvents.OnOperationTypeChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(operationType = event.operationType))
            }

            is PublishEstateEvents.OnPriceChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(price = event.price))
            }

            is PublishEstateEvents.OnSpaceChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(space = event.space))
            }

            is PublishEstateEvents.OnStatusChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(statusOFEstate = event.status))
            }
            is PublishEstateEvents.OnBedsChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(beds = event.beds))
            }
            is PublishEstateEvents.OnBathsChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(baths = event.baths))
            }
            is PublishEstateEvents.OnGarageChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(garage = event.garage))
            }
            is PublishEstateEvents.OnLevelsChanged -> {
                state = state.copy(enteredData = state.enteredData.copy(levels = event.levels))
            }

            is PublishEstateEvents.OnDescriptionChanged -> {
                state =
                    state.copy(enteredData = state.enteredData.copy(description = event.description))
            }

            is PublishEstateEvents.OnDone -> {
                submitData(context = event.context)
            }


            is PublishEstateEvents.OnImageIndexChanged -> {
                state = state.copy(indexOfCurrentImage = event.index)
            }
            is PublishEstateEvents.OnRemoveImage -> {
                removeImage(event.id)
            }
            is PublishEstateEvents.AddNewImage -> {
                updateImagesList(event.updatedImagesList, event.context)
            }
            is PublishEstateEvents.ImageDetectionCautionOk -> {
                deleteTheFakeImage(event.indicesList)
            }

            is PublishEstateEvents.IsLoadingChanged -> {
                state = state.copy(isLoading = event.isLoading)
            }

            is PublishEstateEvents.HideSnackBar -> {
                state = state.copy(
                    dataInvalid = false
                )
            }

            is PublishEstateEvents.OnDoneSuccessSendDataClicked -> {
                state = state.copy(successSendData = false)
            }


            is PublishEstateEvents.WifiCase.Confirm -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is PublishEstateEvents.WifiCase.Deny -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }


        }


    }

    private fun updateImagesList(
        updatedImagesList: List<Uri>,
        context: Context
    ) {
        val newImagesList = state.enteredData.listOfSelectedImages.toMutableList()
        viewModelScope.launch {
            newImagesList += updatedImagesList
            state = state.copy(
                enteredData = state.enteredData.copy(
                    listOfSelectedImages = newImagesList.distinct()
                )
            )
        }
        detectVehicles(newImagesList, context)
    }


    private fun removeImage(id: Int) {
        val newImagesList = state.enteredData.listOfSelectedImages.toMutableList()
        viewModelScope.launch {
            newImagesList.removeAt(id)
            state = state.copy(
                enteredData = state.enteredData.copy(
                    listOfSelectedImages = newImagesList.distinct()
                )
            )
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun detectVehicles(
        imageList: List<Uri>,
        context: Context
    ) {
        //room building plant
        viewModelScope.launch {
            ImageDetectionForEstates.detectEstateInImage(imageList, context,
                onResult = { resultList ->
                    Log.d("Image Detection", "detectVehicles: indices list:  $resultList")
                    /**
                     * resultList contains the indices of each image is not a vehicle.
                     * when update the list of fakeImagesIndexes will update the state.
                     */
                    state = state.copy(
                        fakeImagesIndexes = resultList
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
                    fakeImagesIndexes = emptyList()
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun submitData(context: Context) {

        InternetConnection.run(context,
            connected = {

                if (isValid() && state.enteredData.listOfSelectedImages.isNotEmpty()) {
                    val estate = EstatePublishNewPostRequest(
                        operation_type = state.enteredData.operationType,
                        governorate = state.enteredData.governorate,
                        description = state.enteredData.description,
                        price = state.enteredData.price,
                        address = state.enteredData.address,
                        status = state.enteredData.statusOFEstate,
                        estate_type = state.enteredData.estateType,
                        space = state.enteredData.space.toString(),
                        beds = state.enteredData.beds.toInt(),
                        baths = state.enteredData.baths.toInt(),
                        level = state.enteredData.levels.toInt(),
                        garage = state.enteredData.garage.toInt(),
                        locationInDamascus = "Barzeh"
                    )
                    var listOfImagesFiles: MutableList<MultipartBody.Part>? = null
                    if (state.enteredData.listOfSelectedImages.isNotEmpty()) {
                        val image =
                            ImageHelper.uriToMultipart(
                                context,
                                state.enteredData.listOfSelectedImages[0],
                                "image"
                            )
                        listOfImagesFiles = mutableListOf(image)
                        for (i in 1 until state.enteredData.listOfSelectedImages.size) {
                            listOfImagesFiles.add(
                                ImageHelper.uriToMultipart(
                                    context,
                                    state.enteredData.listOfSelectedImages[i],
                                    "image$i"
                                )
                            )
                        }

                    }


                    viewModelScope.launch {
                        val response = estateApiRepo.postPublishNewEstate(
                            token = TokenManager.getInstance(context = context).getToken(),
                            estate = estate,
                            imageList = listOfImagesFiles
                        )

                        response.collect {
                            when (it) {

                                is Resource.Loading -> {
                                    onEvent(PublishEstateEvents.IsLoadingChanged(isLoading = it.isLoading))

                                }

                                is Resource.Success -> {
                                    if (it.data != null) {

                                        if (it.data.status) {
                                            state = state.copy(
                                                successSendData = true,
                                                enteredData = state.enteredData.copy(
                                                    governorate = "",
                                                    address = "",
                                                    estateType = "",
                                                    operationType = "",
                                                    price = "",
                                                    space = 0f,
                                                    beds = "0",
                                                    baths = "0",
                                                    garage = "0",
                                                    levels = "0",
                                                    description = "",
                                                    statusOFEstate = "",
                                                    listOfSelectedImages = emptyList()

                                                )
                                            )


                                        }
                                    }
                                }

                                is Resource.Error -> {


                                }
                            }
                        }

                    }

                } else {
                    state = state.copy(
                        dataInvalid = true
                    )
                }
            },
            unconnected = {
                state = state.copy(
                    showInternetAlert = true
                )
            })


    }


    private fun isValid(): Boolean {
        return state.enteredData.address.isNotEmpty() &&
                state.enteredData.governorate.isNotEmpty() &&
                state.enteredData.estateType.isNotEmpty() &&
                state.enteredData.operationType.isNotEmpty() &&
                state.enteredData.description.isNotEmpty() &&
                state.enteredData.price.isNotEmpty() &&
                state.enteredData.statusOFEstate.isNotEmpty()

    }


}