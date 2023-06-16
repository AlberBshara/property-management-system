package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm

import android.content.Context
import androidx.navigation.NavHostController

sealed class VehicleHomeEvents {

    data class OnStart(
        val context: Context
    ) : VehicleHomeEvents()

    data class Paginate(
        val context: Context
    ) : VehicleHomeEvents()

    data class SearchQueryChanged(
        val query: String
    ) : VehicleHomeEvents()

    data class OnCancelSearch(
        val context: Context
    ) : VehicleHomeEvents()

    data class OnDoneSearchQuery(
        val query: String,
        val context: Context
    ) : VehicleHomeEvents()

    object ShowSearchBar : VehicleHomeEvents()

    data class ShowDropDownFilter(
        val id: Int
    ) : VehicleHomeEvents()

    data class FilterTypeChanged(
        val filterType: String
    ) : VehicleHomeEvents()

    object LoadingCaseChanged : VehicleHomeEvents()

    data class OnViewMoreClicked(
        val carId: Int,
        val navController: NavHostController
    ) : VehicleHomeEvents()

    data class OnNeedRefresh(
        val context: Context
    ) : VehicleHomeEvents()

}
