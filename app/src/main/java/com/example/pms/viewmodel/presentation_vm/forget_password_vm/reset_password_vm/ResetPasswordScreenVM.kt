package com.example.pms.viewmodel.presentation_vm.forget_password_vm.reset_password_vm

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
import com.example.pms.R
import com.example.pms.model.ResetPassword
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.login_vm.validation.PasswordState
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidateConfirmPassword
import com.example.pms.viewmodel.utils.InternetConnection
import kotlinx.coroutines.launch

class ResetPasswordScreenVM(
    private val userApiRepo: UserServicesRepository = UserServicesRepository(),

    ) : ViewModel() {


    var state by mutableStateOf(ResetPasswordScreenStates())
    private val TAG: String = "ResetPasswordScreenVM.kt"

    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    fun setContext(context: Context) {
        this.context = context
    }

     val passwordState by lazy { mutableStateOf(PasswordState(context)) }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: ResetPasswordScreenEvents) {
        when (event) {
            is ResetPasswordScreenEvents.OnCodeChange -> {
                state = state.copy(
                    code = event.code
                )
            }
            is ResetPasswordScreenEvents.OnPasswordErrorChanged -> {
                state = state.copy(
                    password_error = event.password_error
                )
            }
            is ResetPasswordScreenEvents.OnPasswordChange -> {
                state = state.copy(password = event.password)
                passwordState.value.validate(event.password)
                onEvent(
                    ResetPasswordScreenEvents.OnPasswordErrorChanged(
                        password_error = passwordState.value.error != null
                    )
                )
            }
            is ResetPasswordScreenEvents.OnConfirmPasswordChange -> {
                state = state.copy(
                    confirm_password = event.confirm_password
                )
            }
            is ResetPasswordScreenEvents.OnDoneCode -> {
                submitCode(navHostController = event.navHostController)
            }
            is ResetPasswordScreenEvents.OnDonePassword -> {
                submitResetPassword(
                    navHostController = event.navHostController
                )
            }
            is ResetPasswordScreenEvents.OnShowAlertMessageFromServer -> {
                state = state.copy(
                    showAlertMessageFromServer = !state.showAlertMessageFromServer
                )
            }
            is ResetPasswordScreenEvents.WifiCase.Confirm -> {
                InternetConnection.turnOnWifi(context)
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is ResetPasswordScreenEvents.WifiCase.Deny -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
        }
    }


    @SuppressLint("LongLogTag")
    @RequiresApi(Build.VERSION_CODES.M)
    fun submitCode(navHostController: NavHostController) {

        val code = ResetPassword.SendCodeRequest(code = state.code)
        InternetConnection.run(context,
            connected = {
                viewModelScope.launch {

                    val response = userApiRepo.postSendCodeForgetPassword(code)
                    response.collect {
                        when (it) {
                            is Resource.Loading -> {
                                state = state.copy(progressBarIndicator = it.isLoading)
                            }
                            is Resource.Success -> {
                                val apiResponse = it.data
                                Log.d(TAG, apiResponse.toString())
                                if (apiResponse != null) {
                                    if (apiResponse.status) {
                                        //TODO:(post request has been done successfully)
                                        Toast.makeText(
                                            context,
                                            apiResponse.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        state = state.copy(visiblePassword = true)
                                        state = state.copy(enableButtonSendCode = false)
                                    } else {
                                        //TODO:(post request has been failed for)
                                        state =
                                            state.copy(
                                                errorMessage = apiResponse.errorMessage?.code?.get(0)
                                            )
                                        onEvent(ResetPasswordScreenEvents.OnShowAlertMessageFromServer)
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
                state = state.copy(showInternetAlert = true)
            }
        )
    }

    @SuppressLint("LongLogTag")
    @RequiresApi(Build.VERSION_CODES.M)
    fun submitResetPassword(navHostController: NavHostController) {

        val repeatedPasswordValidate =
            ValidateConfirmPassword.run(state.password, state.confirm_password, context)

        if (!repeatedPasswordValidate.success) {
            state = state.copy(
                confirm_password_error = repeatedPasswordValidate.errorMessage
            )
        }

        val hasError = listOf(
            state.password_error, !repeatedPasswordValidate.success
        ).any {
            it
        }

        if (hasError) {
            Toast.makeText(
                context, context.getString(R.string.uncorrect_info),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val reset_password = ResetPassword.SendResetRequest(
                code = state.code,
                password = state.password,
                password_confirmation = state.confirm_password
            )
            InternetConnection.run(context,
                connected = {
                    viewModelScope.launch {
                        val response = userApiRepo.postSendResetForgetPassword(reset_password)
                        response.collect {
                            when (it) {
                                is Resource.Loading -> {
                                    state = state.copy(progressBarIndicator = it.isLoading)
                                }
                                is Resource.Success -> {
                                    val apiResponse = it.data
                                    Log.d(TAG, apiResponse.toString())
                                    if (apiResponse != null) {
                                        if (apiResponse.status) {
                                            //TODO:(post request has been done successfully)
                                            Toast.makeText(
                                                context,
                                                apiResponse.message,
                                                Toast.LENGTH_LONG
                                            ).show()
                                            navHostController.backQueue.clear()
                                            navHostController.navigate(Destination.LoginDestination.route)
                                        } else {
                                            //TODO:(post request has been failed for)
                                            state = state.copy(
                                                errorMessage = apiResponse.errorMessage?.code?.get(0) + "-" + apiResponse.errorMessage?.password?.get(
                                                    0
                                                )
                                            )
                                            onEvent(ResetPasswordScreenEvents.OnShowAlertMessageFromServer)
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
                    state = state.copy(showInternetAlert = true)
                }
            )
        }
    }
}