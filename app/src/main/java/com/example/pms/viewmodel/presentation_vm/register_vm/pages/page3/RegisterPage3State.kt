package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3


import android.net.Uri

data class RegisterPage3State(
    var emailDuplicated : Boolean = false,
    var isLoading : Boolean = false,
    var showInternetAlert : Boolean = false,
    var image : Uri? = null,
    var requestInternetPermission : Boolean = false
)
