package com.example.pms.viewmodel.presentation_vm.vehicles_vm

import com.example.pms.viewmodel.destinations.Destination

data class VehicleMainState(
    var currentPageRoute : String = Destination.VehiclesHomeDestination.route
)