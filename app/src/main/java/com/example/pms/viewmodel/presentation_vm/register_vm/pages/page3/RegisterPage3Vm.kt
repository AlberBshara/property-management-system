package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterState

class RegisterPage3Vm : ViewModel() {

    var state by mutableStateOf(RegisterState())

    fun page3Done(
        navController : NavHostController
    ){

    }

}