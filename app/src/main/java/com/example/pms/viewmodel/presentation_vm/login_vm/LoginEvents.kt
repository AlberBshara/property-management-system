package com.example.pms.viewmodel.presentation_vm.login_vm

import androidx.navigation.NavHostController

sealed class LoginEvents {
    data class EmailChanged(val email : String) : LoginEvents()
    data class PasswordChanged(val password : String) : LoginEvents()
    data class ForgotMyPassword (val navController : NavHostController) : LoginEvents()
    data class CreateNewAccount(val navController: NavHostController) : LoginEvents()
    data class EmailErrorChanged(val emailError : Boolean) : LoginEvents()
    data class PasswordErrorChanged(val passwordError : Boolean) : LoginEvents()
    data class Submit(val navController: NavHostController) : LoginEvents()
    object ShowDialog : LoginEvents()
    sealed class WifiCase : LoginEvents() {
        object Confirm : WifiCase()

        object Deny : WifiCase()
    }
}
