package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VehicleDetailsScreenVM : ViewModel() {

    var state by mutableStateOf(VehicleDetailsState())

    fun onEvent(event: VehicleDetailsEvents) {
       when(event) {
           is VehicleDetailsEvents.OnCurrentImageIndexChanged -> {
               state = state.copy(
                   currentImageIndex = event.index
               )
           }
       }
    }
}