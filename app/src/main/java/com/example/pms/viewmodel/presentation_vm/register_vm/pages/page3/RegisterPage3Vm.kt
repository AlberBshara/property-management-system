package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.RegisterUserData
import com.example.pms.viewmodel.api.user_services.UserServicesImplementation
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterData
import com.example.pms.viewmodel.utils.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.launch


class RegisterPage3Vm(
    private val userApi: UserServicesImplementation = UserServicesImplementation()
) : ViewModel() {

    private val TAG: String = "RegisterPage3Vm.ky"
    var state by mutableStateOf(RegisterPage3State())

    fun submitData(
        navController: NavHostController,
        context: Context
    ) {

        val registerData =
            navController.previousBackStackEntry?.savedStateHandle?.get<RegisterData>(
                RegisterPages.REGISTER_DATA_KEY
            )

        val user = RegisterUserData(
            name = "${registerData?.firstname!!} ${registerData.lastname}",
            email = registerData.email,
            password = registerData.password,
            phone_number = registerData.phoneNumber,
        )

        viewModelScope.launch {
            val response = userApi.postRegisterUserData(user)
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        Log.d(TAG, "submitData: ${it.isLoading}")
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
                                context
                            )
                        } else {
                            //TODO:(post request has been failed for)
                            signedUpFailed(
                                apiResponse.errorMessage!!,
                                navController,
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "submitData: Exception $it")
                    }
                }
            }
        }
    }

    private fun signedUpSuccessed(
        apiResponse: RegisterUserData.RegisterResponse,
        context: Context
    ) {
        TokenManager.getInstance(context)
            .save(apiResponse.token)
    }

    private fun signedUpFailed(
        errorMessage: String, navController: NavHostController
    ) {
        if (errorMessage.contains("Duplicate")) {
          state.emailDuplicated = true
        }
    }

    fun duplicatedEmail(
        navController: NavHostController
    ) {
       navController.popBackStack()
    }

}