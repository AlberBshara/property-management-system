package com.example.pms.viewmodel.presentation_vm.dashboard_vm

import androidx.lifecycle.ViewModel
import com.example.pms.viewmodel.destinations.Destination

class DashboardScreenVM : ViewModel() {

    fun onEvent(event : DashboardEvents) {
        when(event){
            is DashboardEvents.OnVehicleClicked -> {
                event.navController.navigate(
                    Destination.VehiclesMainDestination.route
                )
            }
        }
    }
}