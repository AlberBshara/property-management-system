package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3

import android.content.Context
import android.graphics.Bitmap
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
import com.example.pms.viewmodel.api.user_services.UserServicesImplementation
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterData
import com.example.pms.viewmodel.utils.InternetConnection
import com.example.pms.viewmodel.utils.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File


class RegisterPage3Vm(
    private val userApi: UserServicesImplementation = UserServicesImplementation()
) : ViewModel() {

    private val TAG : String = "RegisterPage3Vm.ky"
    var state by mutableStateOf(RegisterPage3State())


    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: RegPage3Events) {
        when (event) {
            is RegPage3Events.Submit -> {
                submitData(event.navController, event.context)
            }
            is RegPage3Events.WifiCase.Confirm -> {
                InternetConnection.turnOnWifi(event.context)
                state = state.copy(
                    showInternetAlert = false
                )
                submitData(event.navController, event.context)
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


        val bitmap = state.image

        val boas = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, boas)
        val byteArray = boas.toByteArray()
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(),byteArray)
        val imageFile = MultipartBody.Part.createFormData("file", "image.jpg", requestFile)

        val user = RegisterUserData(
            name = "${registerData?.firstname!!} ${registerData.lastname}",
            email = registerData.email,
            password = registerData.password,
            phone_number = registerData.phoneNumber,
        image = imageFile)

        InternetConnection.run(context,
            connected = {
                viewModelScope.launch {
                    val response = userApi.postRegisterUserData(user)
                    response.collect {
                        when (it) {
                            is Resource.Loading -> {
                                Log.d(TAG, "submitData: ${it.isLoading}")
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
                                    //TODO:(post request has been failed after posting)
                                    signedUpFailed(apiResponse.errorMessage!!)
                                }
                            }
                            is Resource.Error -> {
                                //TODO:(post request has been failed after getting an Exception)
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
        navController.navigate(Destination.DashboardDestination.route) {
            popUpTo(Destination.SplashDestination.route) {
                inclusive = true
            }
        }
    }

    private fun signedUpFailed(
        errorMessage: String
    ) {
        if (errorMessage.contains("Duplicate email")) {
            state.emailDuplicated = true
        }
    }

}