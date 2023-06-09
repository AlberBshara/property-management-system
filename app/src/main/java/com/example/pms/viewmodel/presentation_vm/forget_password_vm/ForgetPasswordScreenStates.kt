package com.example.pms.viewmodel.presentation_vm.forget_password_vm

data class ForgetPasswordScreenStates(
    val email: String = "",
    val showInternetAlert: Boolean = false,
    val showAlertMessageFromServer: Boolean = false,
    var errorMessage: String? = null,
    var progressBarIndicator: Boolean = false,
    var enableButtonSendEmail: Boolean = true,
)
