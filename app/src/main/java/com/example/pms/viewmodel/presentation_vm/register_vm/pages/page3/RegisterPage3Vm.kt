package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.model.RegisterUserData
import com.example.pms.viewmodel.api.user_services.UserServicesImplementation
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterData
import kotlinx.coroutines.launch

class RegisterPage3Vm(
    private val userApi : UserServicesImplementation = UserServicesImplementation()
) : ViewModel() {

    var state by mutableStateOf(RegisterPage3State())

    fun submitData(
        navController : NavHostController
    ){

        val registerData =  navController.previousBackStackEntry?.savedStateHandle?.get<RegisterData>(
            RegisterPages.REGISTER_DATA_KEY
        )

        val user = RegisterUserData(
            name= "${registerData?.firstname!!} ${registerData.lastname}",
            email = registerData.email,
            password = registerData.password,
            phone_number = registerData.phoneNumber,
        )
        viewModelScope.launch {
           userApi.postRegisterUserData(user).collect {
               when(it) {
                   is Resource.Loading -> {
                       Log.d("Testing->", "submitData: ${it.isLoading}")
                   }
                   is Resource.Success -> {
                       Log.d("Testing->", "submitData: ${it.data.toString()}")
                   }
                   is Resource.Error -> {
                       Log.d("Testing->", "submitData: ${it.data}")
                   }
               }
           }
        }

    }

}