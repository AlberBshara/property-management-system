package com.example.pms.viewmodel.presentation_vm.vehicles_vm.postcard_vehicle_vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pms.viewmodel.destinations.Destination

class PostVehicleVM : ViewModel() {

    var state by mutableStateOf(PostVehicleState())

    fun onEvent(event : PostVehicleEvents) {
        when(event) {
            is PostVehicleEvents.LoveChanged -> {
                state = state.copy(
                    loved =  !state.loved
                )
                //TODO: request to add it to mu fav-list.
            }
            is PostVehicleEvents.OnCurrentImageIndexChanged -> {
                state = state.copy(
                    currentImageIndex = event.index
                )
            }
            is PostVehicleEvents.ViewMore -> {
                event.navController.navigate(
                    Destination.VehicleDetailsDestination.route
                )
            }
        }
    }
}