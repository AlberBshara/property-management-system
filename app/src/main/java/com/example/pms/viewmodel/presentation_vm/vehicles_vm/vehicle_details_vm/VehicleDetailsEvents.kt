package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm

sealed class VehicleDetailsEvents {

    data class OnCurrentImageIndexChanged(
        val index : Int
    ) : VehicleDetailsEvents()
}
