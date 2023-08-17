package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page2

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.RegisterWithVerification
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.preferences.UserPreferences
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterData
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidateConfirmPassword
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidateEmail
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidatePassword
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class RegisterPage2Vm(
    private val userServicesRepository: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    var state by mutableStateOf(RegisterPage2State())

    fun onEvent(event: RegPage2Events) {
        when (event) {
            is RegPage2Events.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegPage2Events.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegPage2Events.RepeatedPasswordChanged -> {
                state = state.copy(confirmPassword = event.repeatedPassword)
            }
            is RegPage2Events.OnSubmitClicked -> {
                submit(event.context, event.navController)
            }
            is RegPage2Events.OnDuplicatedEmailClicked -> {
                duplicatedEmail(event.navController)
            }
        }
    }

    private fun submit(
        context: Context,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            val emailValidate = ValidateEmail.run(state.email, context)
            val passwordValidate = ValidatePassword.run(state.password, context)
            val repeatedPasswordValidate =
                ValidateConfirmPassword.run(state.password, state.confirmPassword, context)

            val hasError = listOf(
                emailValidate, passwordValidate, repeatedPasswordValidate
            ).any { !it.success }

            if (hasError) {
                state = state.copy(
                    emailError = emailValidate.errorMessage,
                    passwordError = passwordValidate.errorMessage,
                    confirmPasswordError = repeatedPasswordValidate.errorMessage
                )
            } else {
                state = state.copy(
                    emailError = null, passwordError = null, confirmPasswordError = null
                )
                val response = userServicesRepository.registerEmail(
                    RegisterWithVerification.RegisterEmail(
                        email = state.email, password = state.password
                    )
                )
                response.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            it.data?.let { result ->
                                if (result.status) {
                                    TokenManager.getInstance(context).verified(false)
                                    TokenManager.getInstance(context).completed(false)
                                    UserPreferences.saveUserData(
                                        UserPreferences.UserDataPreference(
                                            it.data.user.user_id, state.email
                                        ), context
                                    )
                                    Log.d(
                                        "Register one", "submit: Success ${it.data.user.user_id}" +
                                                state.email
                                    )
                                    RegisterPages.moveToNextRegisterPage(
                                        navController,
                                        RegisterPages.registerPage4,
                                        RegisterData(email = state.email, password = state.password)
                                    )
                                } else {
                                    TokenManager.getInstance(context).verified(false)
                                    TokenManager.getInstance(context).completed(false)
                                    state = state.copy(
                                        duplicatedEmail = true
                                    )
                                }
                            }
                        }
                        is Resource.Error -> {
                            Log.d("Register one", "submit: Error ${it.message}")
                        }
                    }
                }
            }
        }
    }

    private fun duplicatedEmail(
        navController: NavHostController
    ) {
        state = state.copy(
            duplicatedEmail = false
        )
        navController.navigate(Destination.LoginDestination.route)
    }


}