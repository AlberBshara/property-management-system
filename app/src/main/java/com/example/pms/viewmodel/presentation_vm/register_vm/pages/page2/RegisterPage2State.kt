package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page2

data class RegisterPage2State(
    var email : String = "",
    var emailError : String? = null ,
    var password : String = "",
    var passwordError : String? = null,
    var confirmPassword : String = "",
    var confirmPasswordError : String? = null
)
