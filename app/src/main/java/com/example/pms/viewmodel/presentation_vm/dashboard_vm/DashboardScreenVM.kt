package com.example.pms.viewmodel.presentation_vm.dashboard_vm

import androidx.lifecycle.ViewModel
import com.example.pms.viewmodel.destinations.Destination

class DashboardScreenVM : ViewModel() {

    fun onEvent(event: DashboardEvents) {
        when (event) {
            is DashboardEvents.OnVehicleClicked -> {
                event.navController.navigate(
                    Destination.VehiclesMainDestination.route
                )
            }
            is DashboardEvents.OnSettingClicked -> {
                event.navController.navigate(
                    Destination.SettingsDestination.route
                )
            }
            is DashboardEvents.OnProfileClicked -> {
                event.navController.navigate(
                    Destination.ProfileDestination.route
                )
            }
            is DashboardEvents.OnSuggestionClicked -> {
                event.navController.navigate(
                    Destination.SuggestionsDestination.route
                )
            }
            is DashboardEvents.OnAboutUsClicked -> {
                event.navController.navigate(
                    Destination.AboutUsScreen.route
                )
            }
            is DashboardEvents.OnEstateClicked -> {
                event.nacController.navigate(
                    Destination.EstatesMainDestination.route
                )
            }
        }
    }
}