package com.example.pms.viewmodel.presentation_vm.dashboard_vm

import androidx.navigation.NavHostController

sealed class DashboardEvents {
    data class OnVehicleClicked(
        val navController: NavHostController
    ) : DashboardEvents()

    data class OnEstateClicked(
        val nacController: NavHostController
    ) : DashboardEvents()

    data class OnSettingClicked(
        val navController: NavHostController
    ) : DashboardEvents()

    data class OnProfileClicked(
        val navController: NavHostController
    ) : DashboardEvents()

    data class OnSuggestionClicked(
        val navController: NavHostController
    ) : DashboardEvents()

    data class OnAboutUsClicked(
        val navController: NavHostController
    ) : DashboardEvents()

}
