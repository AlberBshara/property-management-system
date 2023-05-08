package com.example.pms.viewmodel.presentation_vm.vehicles_vm

sealed class VehicleMainEvents {

    data class CurrentPageRouteChanged(
        val route : String
    ) : VehicleMainEvents()


}
