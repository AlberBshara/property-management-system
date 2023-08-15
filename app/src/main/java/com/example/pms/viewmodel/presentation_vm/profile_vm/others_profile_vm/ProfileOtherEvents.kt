package com.example.pms.viewmodel.presentation_vm.profile_vm.others_profile_vm

import android.content.Context
import androidx.navigation.NavHostController

sealed class ProfileOtherEvents {

    data class OnStart(val context: Context, val userId: Int) : ProfileOtherEvents()

    data class GoBackClicked(val navController: NavHostController) : ProfileOtherEvents()

    data class MessageClicked(
        val navController: NavHostController,
        val userId: Int
    ) : ProfileOtherEvents()

    data class OnViewMoreClicked(
        val type: String,
        val typeId: Int,
        val navController: NavHostController,
    ) : ProfileOtherEvents()

    data class LikeClicked(val type: String, val typeId: Int, val context: Context) :
        ProfileOtherEvents()

    object PressOnFacebook : ProfileOtherEvents()
    object PressOnInstagram : ProfileOtherEvents()
    object PressOnTwitter : ProfileOtherEvents()

    data class ChangeCarEstateList(val type: String, val context: Context, val userId: Int) :
        ProfileOtherEvents()

    data class OnReloadClicked(val context: Context, val userId: Int) : ProfileOtherEvents()


}