package com.example.pms.viewmodel.destinations

import androidx.navigation.NavHostController

object RegisterPages {
    const val KEY : String = "pageNumber"
    const val registerPage1 : Int = 0
    const val registerPage2 : Int = 1
    const val registerPage3 : Int = 2

    fun moveToNextRegisterPage(
        navController : NavHostController ,
        pageNumber : Int = registerPage1
    ) {
        navController.currentBackStackEntry?.savedStateHandle
            ?.set(KEY, pageNumber)
        navController.navigate(Destination.RegisterDestination.route)
    }
}