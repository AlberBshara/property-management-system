package com.example.pms.viewmodel.presentation_vm.profile_vm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.EditSocialMediaRequest
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch


class ProfileScreenVM(
    private val userServicesRepository: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
    private var counter: Int = 0

    companion object {
        private const val TAG: String = "ProfileScreenVM.kt"
    }

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.OnStart -> {
                fetchProfileData(event.context)
            }
            is ProfileEvents.OnRefresh -> {
                state = state.copy(
                    isRefreshing = event.isRefreshing
                )
                refresh(event.context)
            }
            is ProfileEvents.EditButton -> {
                  event.navHostController.navigate(Destination.EditProfileInfoDestination.route)
            }
            is ProfileEvents.PressOnFacebook -> {
                state = state.copy(faceBookPress = !state.faceBookPress)
            }
            is ProfileEvents.PressOnInstagram -> {
                state = state.copy(instagramPress = !state.instagramPress)
            }
            is ProfileEvents.PressOnTwitter -> {
                state = state.copy(twitterPress = !state.twitterPress)
            }

            is ProfileEvents.ChangeFaceBookURL -> {
                state = state.copy(faceBookURL = event.url)
            }

            is ProfileEvents.ChangeInstagramURL -> {
                state = state.copy(instagramURL = event.url)
            }

            is ProfileEvents.ChangeTwitterURL -> {
                state = state.copy(twitterURL = event.url)
            }
            is ProfileEvents.OnDoneSendUrl -> {
                editSocialMedia(event.urls, event.context)
            }
            is ProfileEvents.PressOnFavourites -> {
                showFavPosts(event.navHostController)
            }
            is ProfileEvents.PressOnPosts -> {
                showMyPosts(event.navHostController)
            }
            is ProfileEvents.OnReloadClicked -> {
               reload(event.context)
            }
        }
    }

    private fun fetchProfileData(
        context: Context
    ) {
        viewModelScope.launch {
            if (counter == 0) {
                counter++
                val response = userServicesRepository.fetchProfileData(
                    token = TokenManager.getInstance(context).getToken()
                )
                response.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = it.isLoading,
                                isRefreshing = it.isLoading
                            )
                            Log.d(TAG, "fetchProfileData: Loading ${it.isLoading}")
                        }
                        is Resource.Success -> {
                            if (it.data != null) {
                                val user = it.data.user
                                state = state.copy(
                                    name = user.name,
                                    email = user.email,
                                    phone = user.phoneNumber,
                                    location = "syria, Damascus",
                                    imageUrl = user.profileImage,
                                    faceBookURL = user.facebookLink?:"",
                                    instagramURL = user.instagramLink?:"",
                                    twitterURL = user.twitterLink?:"",
                                )
                                Log.d(TAG, "fetchProfileData: Success ${it.data.user}")
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                timeOut = true
                            )
                            Log.d(TAG, "fetchProfileData: Exception ${it.message}")
                        }
                    }
                }

            }
        }
    }


    private fun refresh(
        context: Context
    ) {
        this.counter = 0
        fetchProfileData(context)
    }

    private fun reload(
        context: Context
    ) {
        this.counter = 0
        fetchProfileData(context)
        state = state.copy(
            timeOut = false
        )
    }

    private fun showFavPosts(
        navController : NavHostController
    ) {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            Destination.ProfileHelperScreen.FROM_KEY ,
            Destination.ProfileHelperScreen.FROM_FAV_CLICKED
        )
        navController.navigate(Destination.ProfileHelperScreen.route)
    }

    private fun showMyPosts(
        navController: NavHostController
    ) {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            Destination.ProfileHelperScreen.FROM_KEY ,
            Destination.ProfileHelperScreen.FROM_MY_POST_CLICKED
        )
        navController.navigate(Destination.ProfileHelperScreen.route)
    }


    private fun editSocialMedia(Urls: EditSocialMediaRequest, context: Context) {

        viewModelScope.launch {

            val response = userServicesRepository.editSocialMediaUrl(
                token = TokenManager.getInstance(context).getToken(),
                url = Urls
            )

            response.collect {

                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(loading = it.isLoading)
                    }
                    is Resource.Success -> {
                        if (it.data?.status == true) {
                            Toast.makeText(
                                context,
                                "the URL has been added successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, "there is an error!", Toast.LENGTH_SHORT).show()

                    }
                }

            }


        }
    }

}