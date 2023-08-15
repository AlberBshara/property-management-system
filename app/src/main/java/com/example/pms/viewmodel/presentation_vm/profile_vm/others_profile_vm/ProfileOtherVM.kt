package com.example.pms.viewmodel.presentation_vm.profile_vm.others_profile_vm

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.LikeData
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm.ProfileHelperScreenVM
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class ProfileOtherVM(
    private val userServicesRepository: UserServicesRepository = UserServicesRepository(),
) : ViewModel() {

    var state by mutableStateOf(ProfileOtherState())

    private var counter = 0
    private var counterEstate = 0
    private var counterCar = 0

    fun onEvent(event: ProfileOtherEvents) {
        when (event) {
            is ProfileOtherEvents.OnStart -> {
                onStart(context = event.context, userId = event.userId)
            }
            is ProfileOtherEvents.OnViewMoreClicked -> {
                viewMoreCar(event.type, event.typeId, event.navController)
            }
            is ProfileOtherEvents.GoBackClicked -> {
                event.navController.popBackStack()
            }
            is ProfileOtherEvents.MessageClicked -> {
                startChatting(event.navController, event.userId)
            }
            is ProfileOtherEvents.LikeClicked -> {
                likeCar(event.type, event.typeId, event.context)
            }
            is ProfileOtherEvents.PressOnFacebook -> {
                state = state.copy(
                    faceBookPress = !state.faceBookPress,
                    instagramPress = false,
                    twitterPress = false
                )
            }
            is ProfileOtherEvents.PressOnInstagram -> {
                state = state.copy(
                    instagramPress = !state.instagramPress,
                    faceBookPress = false,
                    twitterPress = false
                )
            }
            is ProfileOtherEvents.PressOnTwitter -> {
                state = state.copy(
                    twitterPress = !state.twitterPress,
                    faceBookPress = false,
                    instagramPress = false
                )
            }
            is ProfileOtherEvents.ChangeCarEstateList -> {
                if (event.type == "estate") {
                    counterEstate = 0
                    state = state.copy(estateClicked = true, carClicked = false)
                    fetchEstatesPosts(context = event.context, userId = event.userId)
                } else {
                    counterCar = 0
                    state = state.copy(estateClicked = false, carClicked = true)
                    fetchCarsPosts(context = event.context, userId = event.userId)
                }
            }
            is ProfileOtherEvents.OnReloadClicked -> {
                onReload(event.userId, event.context)
            }
        }
    }

    private fun onStart(
        context: Context,
        userId: Int
    ) {
        if (counter == 0) {
            counter++
            fetchOtherProfileData(context, userId)
            fetchEstatesPosts(context, userId)
        }
    }

    private fun fetchOtherProfileData(
        context: Context,
        userId: Int
    ) {
        viewModelScope.launch {
            val response = userServicesRepository.fetchOtherProfileData(
                token = TokenManager.getInstance(context).getToken(),
                userId = userId
            )
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoadingProfileData = it.isLoading,
                        )
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            val user = it.data.user
                            state = state.copy(
                                name = user.name,
                                email = user.email,
                                phone = user.phoneNumber,
                                imageUrl = user.profileImage,
                                faceBookURL = user.facebookLink ?: "",
                                instagramURL = user.instagramLink ?: "",
                                twitterURL = user.twitterLink ?: "",
                                needRefresh = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            needRefresh = true
                        )
                    }
                }
            }
        }
    }

    private fun fetchEstatesPosts(
        context: Context, userId: Int
    ) {
        if (counterEstate == 0) {
            counterEstate++
            state = state.copy(vehiclesPostsList = emptyList())
            viewModelScope.launch {
                val myPostResponse = userServicesRepository.getOtherEstates(
                    TokenManager.getInstance(context).getToken(), userId
                )
                myPostResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingEstateCar = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            it.data?.let { myPostResult ->

                                if (myPostResult.posts.isNotEmpty()) {
                                    state = state.copy(
                                        estatesPostsList = myPostResult.posts,
                                        numberOfPosts = myPostResult.posts.size.toString()
                                    )
                                }
                            }
                        }
                        is Resource.Error -> {
                        }
                    }
                }
            }
        }
    }

    private fun fetchCarsPosts(
        context: Context, userId: Int
    ) {
        if (counterCar == 0) {
            counterCar++
            state = state.copy(estatesPostsList = emptyList())
            viewModelScope.launch {
                val myPostResponse = userServicesRepository.getOtherCars(
                    token = TokenManager.getInstance(context).getToken(),
                    userId = userId
                )
                myPostResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingEstateCar = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            it.data?.let { myPostResult ->

                                if (myPostResult.posts.isNotEmpty()) {
                                    state = state.copy(
                                        vehiclesPostsList = myPostResult.posts,
                                        numberOfPosts = myPostResult.posts.size.toString()
                                    )
                                }
                            }
                        }
                        is Resource.Error -> {
                        }
                    }
                }
            }
        }
    }

    private fun likeCar(
        type: String = "car",
        typeId: Int,
        context: Context
    ) {
        viewModelScope.launch {
            val updatedList = state.vehiclesPostsList.toMutableList()
            updatedList.forEachIndexed { index, item ->
                if (item.vehicleData.id == typeId) {
                    updatedList[index] =
                        updatedList[index].copy(liked = !state.vehiclesPostsList[index].liked)
                }
            }
            state = state.copy(vehiclesPostsList = updatedList)

            val response = userServicesRepository.like(
                TokenManager.getInstance(context).getToken(),
                LikeData(typeId, type)
            )
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        it.data?.let { likedResponse ->
                            if (likedResponse.success) {
                                if (likedResponse.message.contains("un")) {
                                    val updatedList1 = state.vehiclesPostsList.toMutableList()
                                    updatedList1.forEachIndexed { index, item ->
                                        if (item.vehicleData.id == typeId) {
                                            updatedList1[index] =
                                                updatedList1[index].copy(liked = true)
                                        }
                                    }
                                } else {
                                    val updatedList1 = state.vehiclesPostsList.toMutableList()
                                    updatedList1.forEachIndexed { index, item ->
                                        if (item.vehicleData.id == typeId) {
                                            updatedList1[index] =
                                                updatedList1[index].copy(liked = false)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is Resource.Error -> {
                        val updatedList1 = state.vehiclesPostsList.toMutableList()
                        updatedList1.forEachIndexed { index, item ->
                            if (item.vehicleData.id == typeId) {
                                updatedList1[index] = updatedList1[index].copy(liked = false)
                            }
                        }
                    }
                }
            }
        }
    }


    private fun viewMoreCar(
        type: String,
        typeId: Int,
        navController: NavHostController
    ) {
        when (type) {
            ProfileHelperScreenVM.VEHICLE -> {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    Destination.CAR_ID_KEY, typeId
                )
                navController.navigate(Destination.VehicleDetailsDestination.route)
            }
            ProfileHelperScreenVM.ESTATE -> {
            }
        }
    }

    private fun onReload(
        userId: Int, context: Context
    ) {
        this.counter = 0
        onStart(context = context, userId = userId)
    }

    private fun startChatting(
        navController: NavHostController,
        userId: Int
    ) {
        viewModelScope.launch {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Destination.MessagesDestination.RECEIVER_ID_KEY, userId
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Destination.MessagesDestination.USER_NAME_KEY, state.name
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Destination.MessagesDestination.IMAGE_URL_KEY, state.imageUrl
            )
            navController.navigate(
                Destination.MessagesDestination.route
            )
        }
    }
}