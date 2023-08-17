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
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.preferences.UserPreferences
import com.example.pms.viewmodel.presentation_vm.login_vm.validation.EmailState
import com.example.pms.viewmodel.presentation_vm.login_vm.validation.PasswordState
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterData
import com.example.pms.viewmodel.utils.InternetConnection
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch


class LoginScreenVM(
    private val userApiRepo: UserServicesRepository = UserServicesRepository()
) : ViewModel() {


    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    fun setContext(context: Context) {
        this.context = context
    }

    companion object {
        private const val TAG: String = "LoginScreenVM.kt"
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
            is LoginEvents.NeedVerify -> {
                verifyAccount(event.navController)
            }
            is LoginEvents.OnCompleteClicked -> {
                completeInfo(event.navController)
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
                                    Log.d(TAG, "submitDataLoading: ${it.isLoading}")
                                    state = state.copy(progressBarIndicator = it.isLoading)
                                }
                                is Resource.Success -> {
                                    val apiResponse = it.data
                                    if (apiResponse != null) {
                                        if (apiResponse.status) {
                                            //TODO:(post request has been done successfully)
                                            Log.d(
                                                TAG,
                                                "submitData,Success: Success ${apiResponse.token}"
                                            )
                                            signedUpSuccessed(apiResponse, navController, context)
                                        } else {
                                            //TODO:(post request has been failed for)
                                            state =
                                                state.copy(errorMessage = "Invalid Data !")
                                            onEvent(LoginEvents.ShowDialog)
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


    private fun signedUpSuccessed(
        apiResponse: LoginUserRequest.LoginResponse,
        navController: NavHostController,
        context: Context
    ) {
        TokenManager.getInstance(context)
            .save(apiResponse.token)
        UserPreferences.saveUserData(
            UserPreferences.UserDataPreference(
                id = apiResponse.user.id,
                email = apiResponse.user.email
            ), context
        )
        if (apiResponse.user.verified == null) {
            state = state.copy(
                needVerify = true
            )
            sendCodeTOGmail(state.email, navController)
        } else if (apiResponse.user.username == null) {
            state = state.copy(
                notCompleted = true
            )
        } else {
            TokenManager.getInstance(context).verified(true)
            TokenManager.getInstance(context).completed(true)
            navController.popBackStack()
            navController.navigate(Destination.DashboardDestination.route)
        }
    }


    private fun verifyAccount(
        navController: NavHostController
    ) {
        state = state.copy(
            needVerify = false
        )
    }


    private fun completeInfo(
        navController: NavHostController
    ) {
        state = state.copy(
            notCompleted = false
        )
        TokenManager.getInstance(context).verified(true)
        RegisterPages.moveToNextRegisterPage(
            navController,
            RegisterPages.registerPage2,
            RegisterData()
        )
    }

    private fun sendCodeTOGmail(
        email: String,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            val response = userApiRepo.sendCodeToGmailToVerify(email)
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(progressBarIndicator = it.isLoading)
                    }
                    is Resource.Success -> {
                        if (it.data?.status == true) {
                            state = state.copy(
                                needVerify = false
                            )
                            RegisterPages.moveToNextRegisterPage(
                                navController,
                                RegisterPages.registerPage4,
                                RegisterData()
                            )
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

}


