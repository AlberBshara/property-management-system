package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VehicleHomeVM : ViewModel() {

    var state by mutableStateOf(VehicleHomeState())

    init {
        state = state.copy(
            isLoading = !state.isLoading
        )
    }

    fun onEvent(event : VehicleHomeEvents) {
        when(event) {
            is VehicleHomeEvents.SearchQueryChanged -> {
                state = state.copy(
                    query = event.query
                )
            }
            is VehicleHomeEvents.ShowSearchBar -> {
                state = state.copy(
                    showSearchBar = !state.showSearchBar
                )
            }
            is VehicleHomeEvents.ShowDropDownFilter -> {
                state = state.copy(
                    showDropDownFilter = !state.showDropDownFilter,
                    filterId = event.id
                )
            }
            is VehicleHomeEvents.FilterTypeChanged -> {
                 state = state.copy(
                     showDropDownFilter = !state.showDropDownFilter,
                     filterType = event.filterType
                 )
            }
            is VehicleHomeEvents.LoadingCaseChanged -> {
                state = state.copy(
                    isLoading =  !state.isLoading
                )
            }
        }
    }
}