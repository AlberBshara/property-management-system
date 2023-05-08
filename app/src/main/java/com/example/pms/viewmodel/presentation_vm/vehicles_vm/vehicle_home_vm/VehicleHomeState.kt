package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm

data class VehicleHomeState(
    var query : String = "",
    var showSearchBar : Boolean = false,
    var showDropDownFilter : Boolean = false,
    var filterType : String = "",
    var filterId : Int = -1
)
