package com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm

import android.net.Uri

data class EditProfileState(
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val location: String = "",
    var imageSend: Uri? = null,
    var imageReceive: String? = null,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    var editImageDialog: Boolean = false,
    var isLoading: Boolean = false,
    var showInternetAlert: Boolean = false,
    var isStarting : Boolean = false ,
)