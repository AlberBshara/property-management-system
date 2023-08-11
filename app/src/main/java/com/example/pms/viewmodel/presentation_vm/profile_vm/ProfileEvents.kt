package com.example.pms.viewmodel.presentation_vm.profile_vm

import android.content.Context
import androidx.navigation.NavHostController
import com.example.pms.model.EditSocialMediaRequest

sealed class ProfileEvents {

    data class OnStart(
        val context: Context
    ) : ProfileEvents()

    data class OnRefresh(
        val isRefreshing: Boolean,
        val context: Context
    ) : ProfileEvents()

    data class EditButton(
        val navHostController: NavHostController
    ) : ProfileEvents()

    object PressOnFacebook : ProfileEvents()
    data class ChangeFaceBookURL(val url: String) : ProfileEvents()

    object PressOnInstagram : ProfileEvents()

    data class ChangeInstagramURL(val url: String) : ProfileEvents()


    object PressOnTwitter : ProfileEvents()

    data class ChangeTwitterURL(val url: String) : ProfileEvents()

    data class OnDoneSendUrl(val urls: EditSocialMediaRequest, val context: Context) : ProfileEvents()

    data class PressOnFavourites(
        val navHostController: NavHostController
    ) : ProfileEvents()

    data class PressOnPosts(
        val navHostController: NavHostController
    ) : ProfileEvents()

    data class OnReloadClicked(
       val context: Context
    ) : ProfileEvents()
}
