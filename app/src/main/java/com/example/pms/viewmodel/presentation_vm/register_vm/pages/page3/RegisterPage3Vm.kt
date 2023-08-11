package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.RegisterUserData
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.preferences.UserPreferences
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterData
import com.example.pms.viewmodel.utils.ImageHelper
import com.example.pms.viewmodel.utils.InternetConnection
import com.example.pms.viewmodel.utils.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.launch


class RegisterPage3Vm(
    private val userApiRepo: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    var state by mutableStateOf(RegisterPage3State())

    companion object {
        private const val TAG: String = "RegisterPage3Vm.ky"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: RegPage3Events) {
        when (event) {
            is RegPage3Events.Submit -> {
                submitData(event.navController, event.context)
            }
            is RegPage3Events.WifiCase.Confirm -> {
                state = state.copy(
                    requestInternetPermission = !state.requestInternetPermission
                )
                InternetConnection.turnOnWifi(event.context)
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is RegPage3Events.WifiCase.Deny -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is RegPage3Events.DuplicatedEmailDone -> {
                event.navController.popBackStack()
            }
            is RegPage3Events.ImageChanged -> {
                state = state.copy(
                    image = event.image
                )
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun submitData(
        navController: NavHostController,
        context: Context
    ) {

        val registerData =
            navController.previousBackStackEntry?.savedStateHandle?.get<RegisterData>(
                RegisterPages.REGISTER_DATA_KEY
            )
        val user: RegisterUserData?

        if (state.image == null) {
            user = RegisterUserData(
                name = "${registerData?.firstname!!} ${registerData.lastname}",
                email = registerData.email,
                password = registerData.password,
                phone_number = registerData.phoneNumber
            )
        } else {
            val file = ImageHelper.uriToMultipart(context, state.image!!, "image")

            user = RegisterUserData(
                name = "${registerData?.firstname!!} ${registerData.lastname}",
                email = registerData.email,
                password = registerData.password,
                phone_number = registerData.phoneNumber,
                image = file
            )
        }
        Log.d(TAG, "submitData: ${user.name} \n ${user.email} ${user.phone_number}")
        InternetConnection.run(context,
            connected = {
                viewModelScope.launch {
                    val response = userApiRepo.postRegisterUserData(user)
                    response.collect {
                        when (it) {
                            is Resource.Loading -> {
                                Log.d(TAG, "submitData: Loading ${it.isLoading}")
                                state = state.copy(
                                    isLoading = it.isLoading
                                )
                            }
                            is Resource.Success -> {
                                val gson = Gson()
                                val apiResponse = gson.fromJson(
                                    it.data?.charStream(),
                                    RegisterUserData.RegisterResponse::class.java
                                )
                                if (apiResponse.status) {
                                    //TODO:(post request has been done successfully)
                                    Log.d(TAG, "submitData: Success ${apiResponse.token}")
                                    signedUpSuccessed(
                                        apiResponse,
                                        context,
                                        navController
                                    )
                                } else {
                                    //TODO: duplicated email:
                                }
                            }
                            is Resource.Error -> {
                                //TODO:(post request has been failed after getting an Exception)
                                Log.d(TAG, "submitData: exception ${it.message}")
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
    }

    private fun signedUpSuccessed(
        apiResponse: RegisterUserData.RegisterResponse,
        context: Context,
        navController: NavHostController
    ) {
        TokenManager.getInstance(context)
            .save(apiResponse.token)
        UserPreferences.saveUserData(
            UserPreferences.UserDataPreference(
                apiResponse.user.user_id,
                apiResponse.user.email
            ), context
        )
        navController.backQueue.clear()
        navController.navigate(Destination.DashboardDestination.route)
    }

    private fun signedUpFailed(jsonResponse: String) {

    }
}
