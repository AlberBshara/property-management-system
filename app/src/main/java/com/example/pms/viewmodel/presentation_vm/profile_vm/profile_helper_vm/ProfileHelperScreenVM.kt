package com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.LikeData
import com.example.pms.model.MyFavResponse
import com.example.pms.model.MyPostsModels
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeVM
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class ProfileHelperScreenVM(
    private val userServicesRepository: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    companion object {
        private const val TAG: String = "ProfileHelperScreenVM.kt"
        const val VEHICLE: String = "car"
        const val ESTATE: String = "estate"
    }

    var state by mutableStateOf(ProfileHelperState())
    private var counter = 0

    fun onEvent(event: ProfileHelperEvents) {
        when (event) {
            is ProfileHelperEvents.OnStart -> {
                onStart(event.from, event.context)
            }
            is ProfileHelperEvents.OnRefresh -> {
                onRefresh(event.from, event.context)
            }
            is ProfileHelperEvents.OnViewMoreClicked -> {
                viewMore(event.type, event.typeId, event.navController)
            }
            is ProfileHelperEvents.GoBackClicked -> {
                event.navController.popBackStack()
            }
            is ProfileHelperEvents.LikeClicked -> {
                like(event.type, event.typeId, event.context)
            }
        }
    }

    private fun onStart(
        from: String, context: Context
    ) {
        if (counter == 0) {
            counter++
            when (from) {
                Destination.ProfileHelperScreen.FROM_FAV_CLICKED -> fetchFavPosts(context, "car")
                Destination.ProfileHelperScreen.FROM_MY_POST_CLICKED -> fetchMyPosts(context, "car")
            }
        }
    }

    private fun onRefresh(
        from: String, context: Context
    ) {
        this.counter = 0
        onStart(from, context)
    }

    @SuppressLint("LongLogTag")
    private fun fetchFavPosts(
        context: Context,
        type: String
    ) {

        viewModelScope.launch {
            val favResponse = userServicesRepository.fetchFavList(
                TokenManager.getInstance(context).getToken(),
                MyFavResponse.MyFavModel(type)
            )
            favResponse.collect {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = it.isLoading
                        )
                    }
                    is Resource.Success -> {
                        it.data?.let { favResult ->
                            Log.d(TAG, "fetchFavPosts: ${favResult.favList}")
                            if (favResult.favList.isNotEmpty()) {
                                state = state.copy(
                                    postsList = favResult.favList
                                )
                            }else{
                                state = state.copy(
                                    noResult = true
                                )
                            }
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

    @SuppressLint("LongLogTag")
    private fun fetchMyPosts(
        context: Context,
        type: String
    ) {
        viewModelScope.launch {
            val myPostResponse = userServicesRepository.fetchMyVehiclePosts(
                TokenManager.getInstance(context).getToken(),
                MyPostsModels.MyVehiclesPostResponse.MyVehiclesPostModel()
            )
            myPostResponse.collect {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = it.isLoading
                        )
                    }
                    is Resource.Success -> {
                        it.data?.let { myPostResult ->
                            if (myPostResult.success) {
                                Log.d(
                                    TAG,
                                    "fetchMyPosts: Success : ${myPostResult.vehiclesPostsList}"
                                )
                                state = if (myPostResult.vehiclesPostsList.isNotEmpty()) {
                                    state.copy(
                                        postsList = myPostResult.vehiclesPostsList
                                    )
                                }else{
                                    state.copy(
                                        noResult = true
                                    )
                                }
                            }
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

    private fun viewMore(
        type: String,
        typeId: Int,
        navController: NavHostController
    ) {
        when (type) {
            VEHICLE -> {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    Destination.CAR_ID_KEY, typeId
                )
                navController.navigate(Destination.VehicleDetailsDestination.route)
            }
            ESTATE -> {

            }
        }
    }


    private fun like(
        type: String = "car",
        typeId: Int,
        context: Context
    ) {
        viewModelScope.launch {
            val updatedList = state.postsList.toMutableList()
            updatedList.forEachIndexed { index, item ->
                if (item.vehicleData.id == typeId) {
                    updatedList[index] =
                        updatedList[index].copy(liked = !state.postsList[index].liked)
                }
            }
            state = state.copy(postsList = updatedList)

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
                                    val updatedList1 = state.postsList.toMutableList()
                                    updatedList1.forEachIndexed { index, item ->
                                        if (item.vehicleData.id == typeId) {
                                            updatedList1[index] =
                                                updatedList1[index].copy(liked = true)
                                        }
                                    }
                                } else {
                                    val updatedList1 = state.postsList.toMutableList()
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
                        val updatedList1 = state.postsList.toMutableList()
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

}