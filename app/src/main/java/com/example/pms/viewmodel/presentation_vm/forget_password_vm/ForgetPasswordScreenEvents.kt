package com.example.pms.viewmodel.presentation_vm.forget_password_vm

import androidx.navigation.NavHostController

sealed class ForgetPasswordScreenEvents {

    data class OnEmailChange(
        val email: String
    ) : ForgetPasswordScreenEvents()

    object OnShowInternetAlertChange : ForgetPasswordScreenEvents()

    object OnShowAlertMessageFromServer : ForgetPasswordScreenEvents()

    sealed class WifiCase : ForgetPasswordScreenEvents() {
        object Confirm : WifiCase()
        object Deny : WifiCase()
    }

    data class OnSendButtonChange(
        val navHostController: NavHostController
    ) : ForgetPasswordScreenEvents()


}
