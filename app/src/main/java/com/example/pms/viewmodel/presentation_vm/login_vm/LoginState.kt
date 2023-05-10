package com.example.pms.viewmodel.presentation_vm.login_vm

data class LoginState(
    var email : String = "" ,
    var emailError : Boolean = true,
    var password: String = "",
    var passwordError : Boolean = true,
    var progressBarIndicator:Boolean=false,
    var errorMessage: String? =null,
    var showDialog:Boolean=false,
    var showInternetAlert : Boolean = false,
)