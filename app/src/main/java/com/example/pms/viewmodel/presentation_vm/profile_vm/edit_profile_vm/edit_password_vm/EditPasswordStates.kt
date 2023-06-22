package com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm.edit_password_vm

data class EditPasswordStates(
    val password: String = "",
    val confirmPassword: String = "",
    var passwordError: Boolean = false,
    val confirmPasswordError: String? = null,
    val oldPassword: String = "",
    var isLoading: Boolean = false,
    var showInternetAlert: Boolean = false,
    var showDialog: Boolean = false,
    var errorMessage: String? = null,
    var successEditing: Boolean = false
)