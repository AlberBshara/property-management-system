package com.example.pms.viewmodel.presentation_vm.forget_password_vm


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.ResetPassword
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.utils.InternetConnection
import kotlinx.coroutines.launch

class ForgetPasswordScreenVM(
    private val userApiRepo: UserServicesRepository = UserServicesRepository(),
) : ViewModel() {


    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    fun setContext(context: Context) {
        this.context = context
    }

    var state by mutableStateOf(ForgetPasswordScreenStates())
    private val TAG: String = "ForgetPasswordScreenVM.kt"


    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: ForgetPasswordScreenEvents) {
        when (event) {
            is ForgetPasswordScreenEvents.OnEmailChange -> {
                state = state.copy(
                    email = event.email
                )
            }
            is ForgetPasswordScreenEvents.OnShowAlertMessageFromServer -> {
                state = state.copy(
                    showAlertMessageFromServer = !state.showAlertMessageFromServer
                )
            }
            is ForgetPasswordScreenEvents.WifiCase.Confirm -> {
                InternetConnection.turnOnWifi(context)
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is ForgetPasswordScreenEvents.WifiCase.Deny -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is ForgetPasswordScreenEvents.OnSendButtonChange -> {
                forgetPassword(navController = event.navHostController)
            }
        }
    }


    @SuppressLint("LongLogTag")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun forgetPassword(
        navController: NavHostController
    ) {

        val email = ResetPassword.SendEmailRequest(email = state.email)

        InternetConnection.run(context,
            connected = {
                viewModelScope.launch {
                    val response = userApiRepo.postSendEmailForgetPassword(email = email)
                    response.collect {
                        when (it) {
                            is Resource.Loading -> {
                                state = state.copy(
                                    progressBarIndicator = it.isLoading,
                                    enableButtonSendEmail = !it.isLoading
                                )
                            }
                            is Resource.Success -> {
                                val apiResponse = it.data
                                Log.d(TAG, apiResponse.toString())

                                if (apiResponse != null) {
                                    if (apiResponse.status) {
                                        //TODO:(post request has been done successfully)
                                        Toast.makeText(
                                            context,
                                            "code has been sent successfully!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        // navController.backQueue.clear()
                                        state = state.copy(email = "")
                                        navController.navigate(Destination.ResetPasswordScreen.route)
                                    } else {
                                        //TODO:(post request has been failed for)
                                        state = state.copy(
                                            errorMessage = apiResponse.errorMessage?.email?.get(0)
                                        )
                                        onEvent(ForgetPasswordScreenEvents.OnShowAlertMessageFromServer)
                                    }
                                }
                            }
                            is Resource.Error -> {
                                Log.d(TAG, "submitData,ResourceError: Exception $it")
                            }
                        }
                    }
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