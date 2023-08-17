package com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.LikeData
import com.example.pms.model.MyFavResponse
import com.example.pms.model.MyFavResponseEstate
import com.example.pms.model.MyPostsModels
import com.example.pms.viewmodel.api.estates_services.EstateServiceImplementation
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.api.vehicels_services.VehicleServicesImplementation
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.utils.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileHelperScreenVM(
    private val userServicesRepository: UserServicesRepository = UserServicesRepository(),
    private val vehicleUserServicesRepository: VehicleServicesImplementation = VehicleServicesImplementation(),
    private val estateServiceImplementation: EstateServiceImplementation = EstateServiceImplementation()
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
                onStart(event.from, event.context, event.type)
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
            is ProfileHelperEvents.OnDeletingPost -> {
                deletePost(event.from, event.context, event.vehicleId, event.vehicleIndex)
            }
            is ProfileHelperEvents.OnVehiclePostsClicked -> {
                vehicleClicked(event.context, event.from)
            }
            is ProfileHelperEvents.OnEstatePostsClicked -> {
                estateClicked(event.context, event.from)
            }
            is ProfileHelperEvents.OnDeletingEstate -> {
            deleteEstate(event.from,event.context, event.estateIndex, event.estateId)
            }
        }
    }

    private fun onStart(
        from: String, context: Context, type: String
    ) {
        if (counter == 0) {
            counter++
            when (from) {
                Destination.ProfileHelperScreen.FROM_FAV_CLICKED -> fetchFavPosts(context, type)
                Destination.ProfileHelperScreen.FROM_MY_POST_CLICKED -> fetchMyPosts(context, type)
            }
        }
    }

    private fun onRefresh(
        from: String, context: Context
    ) {
         this.counter = 0
         onStart(from, context, VEHICLE)
    }

    private fun vehicleClicked(
        context: Context, from: String
    ) {
       this.counter = 0
        onStart(from, context , VEHICLE)
    }

    private fun estateClicked(
        context: Context , from: String
    ) {
        this.counter = 0
        onStart(from, context , ESTATE)
    }

    @SuppressLint("LongLogTag")
    private fun fetchMyPosts(
        context: Context,
        type: String
    ) {
        viewModelScope.launch {
            val myPostResponse = userServicesRepository.fetchMyVehiclePosts(
                TokenManager.getInstance(context).getToken(),
                MyPostsModels.MyVehiclesPostResponse.MyVehiclesPostModel(type)
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
                            val gson = Gson()
                            when (type) {
                                VEHICLE -> {
                                   try {
                                       val vehiclesPosts = gson.fromJson(
                                           myPostResult.charStream(),
                                           MyPostsModels.MyVehiclesPostResponse::class.java
                                       )
                                       state = if (vehiclesPosts.success && vehiclesPosts.vehiclesPostsList.isNotEmpty()) {
                                           state.copy(
                                               estatesPostsList = emptyList(),
                                               vehiclesPostsList = vehiclesPosts.vehiclesPostsList
                                           )
                                       }else{
                                           state.copy(
                                               estatesPostsList = emptyList(),
                                               noResult = true
                                           )
                                       }
                                       Log.d(TAG, "fetchMyPosts: VEHICLES ${vehiclesPosts.vehiclesPostsList}")
                                   }catch (e : Exception){
                                       state = state.copy(
                                           needRefresh = true
                                       )
                                   }
                                }
                                ESTATE -> {
                                   try {
                                       val estatesPosts = gson.fromJson(
                                           myPostResult.charStream(),
                                           MyPostsModels.MyEstatesPostResponse::class.java
                                       )

                                       state = if (estatesPosts.success && estatesPosts.estatesPostsList.isNotEmpty()) {
                                           state.copy(
                                               vehiclesPostsList = emptyList(),
                                               estatesPostsList = estatesPosts.estatesPostsList
                                           )
                                       }else{
                                           state.copy(
                                               vehiclesPostsList = emptyList(),
                                               noResult = true
                                           )
                                       }
                                       Log.d(TAG, "fetchMyPosts: ESTATES ${estatesPosts.estatesPostsList}")
                                   }catch (e : Exception) {
                                       state = state.copy(
                                           needRefresh = true
                                       )
                                   }
                                }
                                else -> {
                                    Log.d(TAG, "fetchMyPosts: something went wrong!")
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

    @SuppressLint("LongLogTag")
    private fun fetchFavPosts(
        context: Context,
        type: String
    ) {
        viewModelScope.launch {
            viewModelScope.launch {
                val myPostResponse = userServicesRepository.fetchFavList(
                    TokenManager.getInstance(context).getToken(),
                   MyFavResponse.MyFavModel(type)
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
                                val gson = Gson()
                                when (type) {
                                    VEHICLE -> {
                                        val vehiclesPosts = gson.fromJson(
                                            myPostResult.charStream(),
                                           MyFavResponse::class.java
                                        )
                                       state = if (vehiclesPosts.favList.isNotEmpty()) {
                                            state.copy(
                                                estatesPostsList = emptyList(),
                                                vehiclesPostsList = vehiclesPosts.favList
                                            )
                                        }else{
                                            state.copy(
                                                estatesPostsList = emptyList(),
                                                noResult = true
                                            )
                                        }
                                        Log.d(TAG, "fetchMyFav: VEHICLES ${vehiclesPosts.favList}")
                                    }
                                    ESTATE -> {
                                        val estatesPosts = gson.fromJson(
                                            myPostResult.charStream(),
                                            MyFavResponseEstate::class.java
                                        )
                                        if (estatesPosts.favList.isNotEmpty()) {
                                            val estateFavList = estatesPosts.favList.map { dto ->
                                                MyPostsModels.MyEstatesPostResponse.PostDataEstates(
                                                    estateData = dto.estateData ,
                                                    images = dto.images,
                                                    liked = dto.liked
                                                )
                                            }
                                            state = state.copy(
                                                vehiclesPostsList = emptyList(),
                                                estatesPostsList = estateFavList
                                            )
                                            Log.d(TAG, "fetchMyFav: ESTATES $estateFavList")
                                        } else {
                                            state = state.copy(
                                                vehiclesPostsList = emptyList(),
                                                noResult = true
                                            )
                                        }
                                    }
                                    else -> {
                                        Log.d(TAG, "fetchMyFav: something went wrong!")
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
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    Destination.ESTATE_ID_KEY, typeId
                )
                navController.navigate(Destination.ViewMoreScreenEstate.route)
            }
        }
    }

    private fun like(
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

    /**
     * the following method will be applicable only with the current user's posts.
     */
    @SuppressLint("LongLogTag")
    private fun deletePost(
        from: String, context: Context,
        vehicleId: Int, vehicleIndex: Int
    ) {
        if (from == Destination.ProfileHelperScreen.FROM_MY_POST_CLICKED) {
            viewModelScope.launch {
                val deletingResponse = vehicleUserServicesRepository.deleteMyVehicle(
                    TokenManager.getInstance(context).getToken(),
                    vehicleId
                )
                deletingResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isDeleting = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            it.data?.let { result ->
                                if (result.success) {
                                    Log.d(TAG, "deletePost: Success ${result.message}")
                                    val updatedList = state.vehiclesPostsList.toMutableList()
                                    updatedList.removeAt(vehicleIndex)
                                    state = state.copy(
                                        vehiclesPostsList = updatedList
                                    )
                                }
                            }
                        }
                        is Resource.Error -> {
                            Log.d(TAG, "deletePost: Error $it")
                        }
                    }
                }
            }
        }
    }

    private fun deleteEstate(
        from: String , context: Context,
        estateIndex: Int , estateId: Int
    ) {
        if (from == Destination.ProfileHelperScreen.FROM_MY_POST_CLICKED){
            viewModelScope.launch {
                val deletingResponse = estateServiceImplementation
                    .deleteMyEstate(
                        TokenManager.getInstance(context).getToken(),
                        estateId
                    )
                deletingResponse.collect {
                    when(it){
                        is Resource.Loading -> {
                            state = state.copy(
                                isDeleting = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            it.data?.let { response ->
                                if (response.success) {
                                    val updatedList = state.estatesPostsList.toMutableList()
                                    updatedList.removeAt(estateIndex)
                                    state = state.copy(
                                        estatesPostsList = updatedList
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
}