package com.example.pms.viewmodel.presentation_vm.vehicles_vm.postcard_vehicle_vm

import androidx.navigation.NavHostController

sealed class PostVehicleEvents {
    object LoveChanged : PostVehicleEvents()

    data class ViewMore(
        val navController: NavHostController,
        val postId : Int = -1
    ) : PostVehicleEvents()
}
