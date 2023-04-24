package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page2

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidateConfirmPassword
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidateEmail
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidatePassword

class RegisterPage2Vm : ViewModel() {

    var state by mutableStateOf(RegisterPage2State())


    fun onEvent(events: RegPage1Events) {
        when (events) {
            is RegPage1Events.EmailChanged -> {
                state = state.copy(email = events.email)
            }
            is RegPage1Events.PasswordChanged -> {
                state = state.copy(password = events.password)
            }
            is RegPage1Events.RepeatedPasswordChanged -> {
                state = state.copy(confirmPassword = events.repeatedPassword)
            }

        }

    }

    fun submitPage1(
        navController: NavHostController,
        context: Context
    ) {
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
            RegisterPages.moveToNextRegisterPage(
                navController = navController,
                pageNumber = RegisterPages.registerPage3
            )
        }
    }


}