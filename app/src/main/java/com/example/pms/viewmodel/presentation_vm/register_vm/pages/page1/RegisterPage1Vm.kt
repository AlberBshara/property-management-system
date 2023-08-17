package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page1

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.EditUserProfileRequest
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterData
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidatePhoneNumber
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidationName
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class RegisterPage1Vm(
    private val userServicesRepository: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    var state by mutableStateOf(RegisterPage1State())


    fun onEvent(event: RegPage1Events) {
        when (event) {
            is RegPage1Events.FirstNameChanged -> {
                state = state.copy(firstname = event.firstname)
            }
            is RegPage1Events.LastNameChanged -> {
                state = state.copy(lastname = event.lastname)
            }
            is RegPage1Events.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phoneNumber, countryCode = event.countryCode)
            }
        }
    }

    fun submitPage1(
        navController: NavHostController,
        context: Context
    ) {

        val firstnameValidate = ValidationName.run(state.firstname, context)
        val lastnameValidate = ValidationName.run(state.lastname, context)
        val phoneNumberValidate =
            ValidatePhoneNumber.run(state.phoneNumber, state.countryCode, context)

        val hasError = listOf(
            firstnameValidate, lastnameValidate, phoneNumberValidate
        ).any { !it.success }

        if (hasError) {
            state = state.copy(
                firstname_error = firstnameValidate.errorMessage,
                lastname_error = lastnameValidate.errorMessage,
                phoneNumber_error = phoneNumberValidate.errorMessage
            )
        } else {
            val phoneNumber = if (state.countryCode == "000"){
                state.phoneNumber
            }else{
               "+" + state.countryCode + " " + state.phoneNumber
            }
            val registerData = RegisterData(
                firstname = state.firstname,
                lastname = state.lastname,
                phoneNumber = phoneNumber,
            )
            state = state.copy(
                firstname_error = null,
                lastname_error = null,
                phoneNumber_error = null
            )
            viewModelScope.launch {
                val response = userServicesRepository.postUpdateUserInfo(
                    TokenManager.getInstance(context).getToken(),
                    EditUserProfileRequest(
                        name = state.firstname + " " + state.lastname,
                        phoneNumber = phoneNumber
                    )
                )
                response.collect {
                    when(it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            it.data?.let { result ->
                                if (result.status) {
                                    TokenManager.getInstance(context).completed(true)
                                    RegisterPages.moveToNextRegisterPage(
                                        navController = navController,
                                        pageNumber = RegisterPages.registerPage3,
                                        registerData
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