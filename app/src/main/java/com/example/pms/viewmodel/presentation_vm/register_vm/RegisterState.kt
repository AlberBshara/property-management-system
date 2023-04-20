package com.example.pms.viewmodel.presentation_vm.register_vm


data class RegisterState(
    val firstname : String,
    val lastname : String ,
    val phoneNumber : String ,
    val email : String ,
    val password : String,
    val confirmPassword : String,
    //val image : Int
)
