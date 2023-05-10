package com.example.pms.viewmodel.presentation_vm.login_vm


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.model.LoginUserRequest
import com.example.pms.viewmodel.api.user_services.UserServicesImplementation
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.login_vm.validation.EmailState
import com.example.pms.viewmodel.presentation_vm.login_vm.validation.PasswordState
import com.example.pms.viewmodel.utils.InternetConnection
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch


class LoginScreenVM(
    private val userApiRepo: UserServicesImplementation = UserServicesImplementation()
) : ViewModel() {


    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    fun setContext(context: Context) {
        this.context = context
    }

    var state by mutableStateOf(LoginState())

    val emailState by lazy { mutableStateOf(EmailState(context)) }
    val passwordState by lazy { mutableStateOf(PasswordState(context)) }


    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.EmailChanged -> {
                state = state.copy(email = event.email)
                emailState.value.validate(event.email)
                onEvent(LoginEvents.EmailErrorChanged(emailState.value.error != null))
            }
            is LoginEvents.EmailErrorChanged -> {
                state = state.copy(emailError = event.emailError)
            }
            is LoginEvents.PasswordChanged -> {
                state = state.copy(password = event.password)
                passwordState.value.validate(event.password)
                onEvent(LoginEvents.PasswordErrorChanged(passwordState.value.error != null))
            }
            is LoginEvents.PasswordErrorChanged -> {
                state = state.copy(passwordError = event.passwordError)
            }
            is LoginEvents.ForgotMyPassword -> {
                event.navController.navigate(Destination.ForgetPasswordDestination.route)
            }
            is LoginEvents.CreateNewAccount -> {
                event.navController.navigate(Destination.RegisterDestination.route)
            }
            is LoginEvents.Submit -> {
                login(event.navController)
            }
            is LoginEvents.ShowDialog -> {
                state = state.copy(
                    showDialog = !state.showDialog
                )
            }
            is LoginEvents.WifiCase.Confirm -> {
                InternetConnection.turnOnWifi(context)
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is LoginEvents.WifiCase.Deny -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun login(
        navController: NavHostController
    ) {
        val hasError = listOf(
            state.emailError, state.passwordError
        ).any {
            it
        }
        if (hasError) {
            Toast.makeText(
                context, context.getString(R.string.uncorrect_info),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            val user =
                LoginUserRequest(email = state.email, password = state.password)
            InternetConnection.run(context,
                connected = {
                    viewModelScope.launch {
                        val response = userApiRepo.postLoginUserData(user = user)
                        response.collect {
                            when (it) {
                                is Resource.Loading -> {
                                    Log.d("jojo", "submitDataLoading: ${it.isLoading}")
                                    state = state.copy(progressBarIndicator = it.isLoading)
                                }
                                is Resource.Success -> {
                                    val apiResponse = it.data
                                    if (apiResponse != null) {
                                        if (apiResponse.status) {
                                            //TODO:(post request has been done successfully)
                                            Log.d(
                                                "jojo",
                                                "submitData,Success: Success ${apiResponse.token}"
                                            )
                                            signedUpSuccessed(apiResponse, navController, context)
                                        } else {
                                            //TODO:(post request has been failed for)
                                            state =
                                                state.copy(errorMessage = apiResponse.errorMessage)
                                            onEvent(LoginEvents.ShowDialog)
                                        }
                                    }
                                }
                                is Resource.Error -> {
                                    Log.d("jojo", "submitData,ResourceError: Exception $it")
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


    private fun signedUpSuccessed(
        apiResponse: LoginUserRequest.LoginResponse, navController: NavHostController,
        context: Context
    ) {
        TokenManager.getInstance(context)
            .save(apiResponse.token)
        navController.popBackStack()
        navController.navigate(Destination.DashboardDestination.route)
    }


}


