package com.example.pms.viewmodel.presentation_vm.estates_vm.view_more_estate_vm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.model.FavouriteEstates
import com.example.pms.model.RateEstate
import com.example.pms.viewmodel.api.estates_services.EstateServiceImplementation
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class ViewMoreEstateScreenVM(
    private val estateApiRepo: EstateServiceImplementation = EstateServiceImplementation()
) : ViewModel() {


    var state by mutableStateOf(ViewMoreEstateStates())
    private var counter = 0

    fun onEvent(event: ViewMoreEstateEvents) {
        when (event) {
            is ViewMoreEstateEvents.ChangeImage -> {
                state = state.copy(currentImageIndex = event.indexOfImage)
            }
            is ViewMoreEstateEvents.OnGetDataFromServer -> {
                getEstateDataById(context = event.context, estateId = event.estateId)
            }
            is ViewMoreEstateEvents.OnCallPhoneClicked -> {
                makingCall(event.phoneNumber, event.context)
            }
            is ViewMoreEstateEvents.OnCurrentImageIndexChanged -> {
                state = state.copy(
                    currentImageIndex = event.index
                )
            }
            is ViewMoreEstateEvents.OnLikeClicked -> {
                addOrRemoveFromFavourites(context = event.context, estateId = event.estateId)
            }
            is ViewMoreEstateEvents.OnShareClicked -> {
                shareEstateData(event.context)
            }
            is ViewMoreEstateEvents.OnVisitProfileClicked -> {
                event.navHostController.navigate(Destination.ProfileDestination.route)
            }
            is ViewMoreEstateEvents.OnChangeShowRateScreen -> {
                state = state.copy(isShowingRateScreen = !state.isShowingRateScreen)
            }
            is ViewMoreEstateEvents.OnAddRateEstate -> {
                addRateEstate(estateId = event.estateId, rate = event.rate, context = event.context)
            }
            is ViewMoreEstateEvents.OnChattingClicked -> {
                startChatting(
                    event.navController, event.receiverId,
                    event.receiverUsername, event.receiverImageUrl
                )
            }
        }
    }

    @SuppressLint("LongLogTag")
    private fun getEstateDataById(context: Context, estateId: Int) {
        if (counter == 0) {
            counter++
            state = state.copy(
                isLoading = true
            )
            //GetEstateData
            viewModelScope.launch {
                val response = estateApiRepo.getEstateById(
                    TokenManager.getInstance(context).getToken(),
                    estateId
                )
                response.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            if (it.data != null) {
                                if (it.data.status) {
                                    val estate = it.data.estate
                                    state = state.copy(
                                        estateType = estate.estate_type,
                                        operationType = estate.operation_type,
                                        status = estate.status,
                                        governorate = estate.governorate,
                                        address = estate.address,
                                        description = estate.description,
                                        price = estate.price.toInt(),
                                        space = estate.space.toInt(),
                                        beds = estate.beds.toInt(),
                                        baths = estate.baths.toInt(),
                                        garage = estate.garage.toInt(),
                                        levels = estate.level.toInt(),
                                        //  imagesList = estate.images,
                                        name = it.data.owner.name,
                                        number = it.data.owner.phone_number,
                                        loved = it.data.favourite,
                                        userId = it.data.owner.id,
                                        userImageUrl = it.data.owner.image
                                    )
                                    getNumberOfLikesFromServer(context, estateId)
                                    getRateFromServer(context, estateId)
                                    try {
                                        if (it.data.images.isNotEmpty()) {
                                            state = state.copy(
                                                imagesList = it.data.images.toMutableList()
                                            )
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()

                                    }
                                }
                            }
                        }
                        is Resource.Error -> {
                            Log.d("kok", "getVehicleDataById: ${it.message}")
                        }
                    }
                }
            }
        }
    }

    private fun getRateFromServer(context: Context, estateId: Int) {
        //GetRate
        viewModelScope.launch {
            val responseRate = estateApiRepo.getRateEstate(
                token = TokenManager.getInstance(context).getToken(),
                rate = RateEstate.GetRateEstateRequest(type = "estate", estateId = estateId)
            )
            responseRate.collect {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        if (it.data?.status == true) {
                            state = state.copy(rate = it.data.rate)
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }

        }
    }


    private fun addRateEstate(context: Context, estateId: Int, rate: Int) {
        //AddRate
        viewModelScope.launch {
            val responseRate = estateApiRepo.addRateEstate(
                token = TokenManager.getInstance(context).getToken(),
                rate = RateEstate.AddRateEstateRequest(
                    type = "estate",
                    estate_id = estateId,
                    rate = rate
                )
            )
            responseRate.collect {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        if (it.data?.status == true) {
                            Toast.makeText(context, "rated Successfully", Toast.LENGTH_SHORT).show()
                            onEvent(ViewMoreEstateEvents.OnChangeShowRateScreen)
                            getRateFromServer(context, estateId)
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }


    private fun getNumberOfLikesFromServer(context: Context, estateId: Int) {
        //GetNumberOfLikesRequest
        viewModelScope.launch {
            val idAndType = FavouriteEstates.GetNumberOfLikesRequest(id = estateId, type = "estate")
            val responseLikes = estateApiRepo.getNumOfLikesEstate(
                token = TokenManager.getInstance(context).getToken(),
                idAndType = idAndType
            )
            responseLikes.collect {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            state = state.copy(numberOfLikes = it.data.Likes_number)
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    private fun addOrRemoveFromFavourites(context: Context, estateId: Int) {
        viewModelScope.launch {
            val response = estateApiRepo.addOrRemoveFromFavourites(
                token = TokenManager.getInstance(context).getToken(),
                favourite = FavouriteEstates.AddOrRemoveFromFavouritesRequest(
                    id = estateId,
                    type = "estate"
                )
            )
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        if (it.data?.status == true) {
                            state = state.copy(loved = !state.loved)
                            getNumberOfLikesFromServer(context, estateId)
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }


    private fun makingCall(phoneNumber: String, context: Context) {
        viewModelScope.launch {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:$phoneNumber")
            context.startActivity(dialIntent)
        }
    }

    private fun startChatting(
        navController: NavHostController,
        receiverId: Int, receiverUsername: String, receiverImageUrl: String?
    ) {
        viewModelScope.launch {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Destination.MessagesDestination.RECEIVER_ID_KEY, receiverId
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Destination.MessagesDestination.USER_NAME_KEY, receiverUsername
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Destination.MessagesDestination.IMAGE_URL_KEY, receiverImageUrl
            )
            navController.navigate(
                Destination.MessagesDestination.route
            )
        }
    }


    private fun shareEstateData(context: Context) {
        viewModelScope.launch {
            val vehicleData = "${context.getString(R.string.estate_details)} \n" +
                    "${context.getString(R.string.estate_type)} : ${state.estateType}\n" +
                    "${context.getString(R.string.price)} : ${state.price}\n" +
                    "${context.getString(R.string.location)} : ${state.address}\n" +
                    "${context.getString(R.string.space)} : ${state.space}\n" +
                    "${context.getString(R.string.level)} : ${state.levels}\n" +
                    "${context.getString(R.string.garage)} : ${state.garage}\n" +
                    "${context.getString(R.string.baths)} : ${state.baths}\n" +
                    "${context.getString(R.string.bed)} : ${state.beds}\n" +
                    "${context.getString(R.string.operation)} : ${state.operationType}\n" +
                    "${context.getString(R.string.owner)} : ${state.name}\n" +
                    "${context.getString(R.string.description)} : ${state.description}\n" +
                    "${context.getString(R.string.phone)} : ${state.number}"

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

