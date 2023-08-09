package com.example.pms.viewmodel.presentation_vm.estates_vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EstateMainScreenVM : ViewModel() {


    var state by mutableStateOf(EstateMainState())

    fun onEvent(event: EstateMainEvents) {
        when (event) {

            is EstateMainEvents.CurrentPageRouteChanged -> {
                state = state.copy(currentPageRoute = event.route)
            }


        }


    }


}