package com.example.pms.viewmodel.presentation_vm.login_vm

data class LoginState(
    var email : String = "" ,
    var emailState : String? = null,
    var password: String = "",
    var passwordError : String? = null
)