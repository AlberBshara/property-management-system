package com.example.pms.viewmodel.presentation_vm.profile_vm

import android.content.Context
import androidx.navigation.NavHostController

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

    data class PressOnFacebook(
        val navHostController: NavHostController
    ) : ProfileEvents()

    data class PressOnInstagram(
        val navHostController: NavHostController
    ) : ProfileEvents()

    data class PressOnTwitter(
        val navHostController: NavHostController
    ) : ProfileEvents()

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
