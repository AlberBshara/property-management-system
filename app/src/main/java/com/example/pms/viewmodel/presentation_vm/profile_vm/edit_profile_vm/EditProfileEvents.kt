package com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm


import android.content.Context
import android.net.Uri
import androidx.navigation.NavHostController

sealed class EditProfileEvents {

    data class ChangeFirstName(
        val firstName: String
    ) : EditProfileEvents()

    data class ChangeLastName(
        val lastName: String
    ) : EditProfileEvents()

    data class ChangePhoneNumber(
        val phone: String
    ) : EditProfileEvents()

    data class ChangeImage(
        val image: Uri,
        val context: Context
    ) : EditProfileEvents()

    data class ChangeImageReceive(
        val imageReceive: String
    ) : EditProfileEvents()


    data class Submit(
        val context: Context
    ) : EditProfileEvents()

    data class ResetPasswordButton(
        val navHostController: NavHostController
    ) : EditProfileEvents()

    object EditImageDialogOnChange : EditProfileEvents()

    data class IsLoadingChanged(
        val isLoading: Boolean
    ) : EditProfileEvents()

    object OnDoneSuccessSendDataClicked : EditProfileEvents()

    sealed class WifiCase : EditProfileEvents() {
        object Confirm : WifiCase()

        object Deny : WifiCase()
    }

    data class GetUserData(
        val context : Context
    ) : EditProfileEvents()
}
