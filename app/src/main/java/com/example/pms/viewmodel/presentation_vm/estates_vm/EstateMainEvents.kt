package com.example.pms.viewmodel.presentation_vm.estates_vm

sealed class EstateMainEvents {

    data class CurrentPageRouteChanged(val route: String) : EstateMainEvents()


}