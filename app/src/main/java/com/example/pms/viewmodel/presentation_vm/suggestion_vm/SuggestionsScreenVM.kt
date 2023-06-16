package com.example.pms.viewmodel.presentation_vm.suggestion_vm

import androidx.lifecycle.ViewModel

class SuggestionsScreenVM : ViewModel() {


    fun onEvent(event: SuggestionsEvents) {
        when (event) {
            is SuggestionsEvents.OnFindingGarageClicked -> {

            }
            is SuggestionsEvents.OnFindingWashGarageClicked -> {

            }
            is SuggestionsEvents.OnNewsClicked -> {

            }
            is SuggestionsEvents.OnRateCarClicked -> {

            }
        }
    }

}