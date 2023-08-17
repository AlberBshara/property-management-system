package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.utils.ImageHelper
import com.example.pms.viewmodel.utils.InternetConnection
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch


class RegisterPage3Vm(
    private val userApiRepo: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    var state by mutableStateOf(RegisterPage3State())


    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: RegPage3Events) {
        when (event) {
            is RegPage3Events.Submit -> {
                submitData(event.navController, event.context)
            }
            is RegPage3Events.WifiCase.Confirm -> {
                state = state.copy(
                    requestInternetPermission = !state.requestInternetPermission
                )
                InternetConnection.turnOnWifi(event.context)
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is RegPage3Events.WifiCase.Deny -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is RegPage3Events.DuplicatedEmailDone -> {
                event.navController.popBackStack()
            }
            is RegPage3Events.ImageChanged -> {
                state = state.copy(
                    image = event.image
                )
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun submitData(
        navController: NavHostController,
        context: Context
    ) {
        if (state.image == null) {
            navController.popBackStack()
            navController.navigate(Destination.DashboardDestination.route)
        }else{
            viewModelScope.launch {
                val imageFile = ImageHelper.singleUriToMultipart(context, state.image!!,"image")
                val response = userApiRepo.postEditUserImage(
                    TokenManager.getInstance(context).getToken(),
                    imageFile
                )
                response.collect {
                    when(it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            it.data?.let { result ->
                                if (result.status) {
                                    TokenManager.getInstance(context).completed(true)
                                    navController.popBackStack()
                                    navController.navigate(Destination.DashboardDestination.route)
                                }
                            }
                        }
                        is Resource.Error -> {

                        }
                    }
                }
            }
        }
    }

}
