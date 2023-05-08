package com.example.pms.viewmodel.presentation_vm.vehicles_vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VehiclesMainScreenVm : ViewModel() {

    var state by mutableStateOf(VehicleMainState())

    fun onEvent(event : VehicleMainEvents) {
       when(event) {
           is VehicleMainEvents.CurrentPageRouteChanged -> {
               state = state.copy(
                   currentPageRoute = event.route
               )
           }

       }
    }
}