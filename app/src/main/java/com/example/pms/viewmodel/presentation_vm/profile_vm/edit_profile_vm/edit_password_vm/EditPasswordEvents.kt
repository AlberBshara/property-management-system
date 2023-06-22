package com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm.edit_password_vm


sealed class EditPasswordEvents {

    data class ChangeOldPassword(
        val oldPassword: String
    ) : EditPasswordEvents()

    data class ChangePassword(
        val password: String
    ) : EditPasswordEvents()

    data class ChangeConfirmPassword(
        val confirmPassword: String
    ) : EditPasswordEvents()

    data class PasswordErrorChanged(
        val passwordError: Boolean
    ) : EditPasswordEvents()

    data class SuccessEditingPasswordChanged(
        val success: Boolean
    ) : EditPasswordEvents()

    object Submit : EditPasswordEvents()

    data class IsLoadingChanged(
        val isLoading: Boolean
    ) : EditPasswordEvents()

    sealed class WifiCase : EditPasswordEvents() {
        object Confirm : WifiCase()

        object Deny : WifiCase()
    }

    object ShowDialog : EditPasswordEvents()

}
