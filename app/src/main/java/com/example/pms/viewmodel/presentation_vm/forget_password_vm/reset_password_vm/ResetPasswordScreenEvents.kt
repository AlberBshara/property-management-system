package com.example.pms.viewmodel.presentation_vm.forget_password_vm.reset_password_vm


import androidx.navigation.NavHostController

sealed class ResetPasswordScreenEvents {


    data class OnCodeChange(
        val code: String
    ) : ResetPasswordScreenEvents()

    data class OnPasswordChange(
        val password: String
    ) : ResetPasswordScreenEvents()

    data class OnConfirmPasswordChange(
        val confirm_password: String
    ) : ResetPasswordScreenEvents()

    data class OnPasswordErrorChanged(
        val password_error: Boolean
    ) : ResetPasswordScreenEvents()

    data class OnDoneCode(
        val navHostController: NavHostController
    ) : ResetPasswordScreenEvents()

    data class OnDonePassword(
        val navHostController: NavHostController
    ) : ResetPasswordScreenEvents()

    object OnShowInternetAlertChange : ResetPasswordScreenEvents()

    object OnShowAlertMessageFromServer : ResetPasswordScreenEvents()

    sealed class WifiCase : ResetPasswordScreenEvents() {
        object Confirm : WifiCase()
        object Deny : WifiCase()
    }
}
