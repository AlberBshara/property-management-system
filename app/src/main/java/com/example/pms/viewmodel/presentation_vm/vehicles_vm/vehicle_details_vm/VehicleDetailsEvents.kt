package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm

import android.content.Context

sealed class VehicleDetailsEvents {

    data class OnStart(
        val context: Context,
        val carId : Int
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
        val carId : Int
    ) : VehicleDetailsEvents()
}
