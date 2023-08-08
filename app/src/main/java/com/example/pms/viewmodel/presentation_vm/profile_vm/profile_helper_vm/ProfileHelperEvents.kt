package com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm

import android.content.Context
import androidx.navigation.NavHostController

sealed class ProfileHelperEvents {

    data class OnStart(
        val from: String,
        val context: Context
    ) : ProfileHelperEvents()

    data class OnRefresh(
        val from: String,
        val context: Context
    ) : ProfileHelperEvents()

    data class OnViewMoreClicked(
        val type: String,
        val typeId: Int,
        val navController: NavHostController,
    ) : ProfileHelperEvents()

    data class GoBackClicked(
        val navController: NavHostController
    ) : ProfileHelperEvents()

    data class LikeClicked(
        val type: String,
        val typeId: Int,
        val context: Context
    ) : ProfileHelperEvents()

    data class OnDeletingPost(
        val from: String,
        val vehicleIndex: Int,
        val vehicleId: Int,
        val context: Context
    ) : ProfileHelperEvents()
}
