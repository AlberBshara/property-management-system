package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm

import android.content.Context
import androidx.navigation.NavHostController

sealed class VehicleDetailsEvents {

    data class OnStart(
        val context: Context,
        val carId: Int
    ) : VehicleDetailsEvents()

    data class OnCurrentImageIndexChanged(
        val index: Int
    ) : VehicleDetailsEvents()

    data class OnCallPhoneClicked(
        val phoneNumber: String,
        val context: Context
    ) : VehicleDetailsEvents()

    data class OnShareClicked(
        val context: Context
    ) : VehicleDetailsEvents()

    data class OnReloadClicked(
        val context: Context,
        val carId: Int
    ) : VehicleDetailsEvents()

    data class LikeClicked(
        val carId: Int,
        val context: Context
    ) : VehicleDetailsEvents()

    data class OnRefresh(
        val carId: Int,
        val context: Context
    ) : VehicleDetailsEvents()

    data class OnRatingPicked(
        val carId: Int,
        val ratingVal: Int,
        val context: Context
    ) : VehicleDetailsEvents()

    object OnRatingClicked : VehicleDetailsEvents()

    data class OnStartMessagingClicked(
        val navController: NavHostController,
        val receiverId: Int,
        val receiverUsername: String,
        val receiverImageUrl: String?
    ) : VehicleDetailsEvents()

    data class OnVisitProfileClicked(
        val navController: NavHostController,
        val userId: Int
    ) : VehicleDetailsEvents()
}
