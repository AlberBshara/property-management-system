package com.example.pms.viewmodel.presentation_vm.register_vm


data class RegisterState(
    var firstname : String = "",
    var lastname : String = "",
    var phoneNumber : String = "",
    var email : String = "",
    var password : String = "",
    var confirmPassword : String = "",
    //val image : Int
)
