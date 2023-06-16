package com.example.pms.viewmodel.presentation_vm.suggestion_vm

import androidx.navigation.NavHostController

sealed class SuggestionsEvents {

    data class OnFindingGarageClicked(
        val navController: NavHostController
    ) : SuggestionsEvents()

    data class OnFindingWashGarageClicked(
        val navController: NavHostController
    ) : SuggestionsEvents()

    data class OnNewsClicked(
        val navController: NavHostController
    ) : SuggestionsEvents()

    data class OnRateCarClicked(
        val nacController: NavHostController
    ) : SuggestionsEvents()
}
