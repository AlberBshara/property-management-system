package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm

sealed class VehicleHomeEvents {

    data class SearchQueryChanged(
        val query: String
    ) : VehicleHomeEvents()

    object ShowSearchBar : VehicleHomeEvents()

    data class ShowDropDownFilter(
        val id : Int
    ) : VehicleHomeEvents()

    data class FilterTypeChanged(
       val filterType : String
    ) : VehicleHomeEvents()

    object LoadingCaseChanged : VehicleHomeEvents()


}
