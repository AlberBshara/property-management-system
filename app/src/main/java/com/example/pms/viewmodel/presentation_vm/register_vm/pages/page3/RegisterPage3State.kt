package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3

data class RegisterPage3State(
    var image : Int = -1,
    var emailDuplicated : Boolean = false,
    var isLoading : Boolean = false,
    var showInternetAlert : Boolean = false
)
