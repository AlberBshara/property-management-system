package com.example.pms.viewmodel.presentation_vm.forget_password_vm.reset_password_vm

data class ResetPasswordScreenStates(
    val code: String = "",
    val password: String = "",
    val confirm_password: String = "",
    val password_error: Boolean = false,
    val confirm_password_error: String? = null,


    val showInternetAlert: Boolean = false,
    val showAlertMessageFromServer: Boolean = false,
    var errorMessage: String? = null,
    var progressBarIndicator: Boolean = false,

    var visiblePassword: Boolean = false,
    var enableButtonSendCode: Boolean = true,
)
