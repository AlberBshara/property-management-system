package com.example.pms.viewmodel.destinations

import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterData

object RegisterPages {
    const val KEY : String = "pageNumber"
    const val REGISTER_DATA_KEY : String = "RegisterData"
    const val registerPage1 : Int = 0
    const val registerPage2 : Int = 1
    const val registerPage3 : Int = 2

     fun moveToNextRegisterPage(
        navController : NavHostController ,
        pageNumber : Int = registerPage1,
        registerData : RegisterData
    ) {
        navController.currentBackStackEntry?.savedStateHandle
            ?.set(KEY, pageNumber)
         navController.currentBackStackEntry?.savedStateHandle
             ?.set(REGISTER_DATA_KEY, registerData)
        navController.navigate(Destination.RegisterDestination.route)
    }
}