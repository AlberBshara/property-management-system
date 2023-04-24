package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page1

data class RegisterPage1State(
    var firstname : String = "" ,
    var firstname_error : String? = null ,
    var lastname : String = "" ,
    var lastname_error : String? = null ,
    var phoneNumber : String = "",
    var phoneNumber_error: String? = null
)
