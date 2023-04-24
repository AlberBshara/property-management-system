package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page1

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.destinations.RegisterPages

class RegisterPage1Vm : ViewModel() {

    var state by mutableStateOf(RegisterPage1State())

    fun page1Done(
        navController: NavHostController,
    ){

        RegisterPages.moveToNextRegisterPage(
            navController = navController,
            pageNumber = RegisterPages.registerPage2
        )
    }
}