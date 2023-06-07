package com.example.pms.viewmodel.presentation_vm.settings_vm

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class SettingsScreenVM(
    private val userApiRepo: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    var state by mutableStateOf(SettingsState())

    private val TAG: String = "SettingsScreenVM.kt"

    fun onEvent(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.OnNotificationsCaseChanged -> {
                state = state.copy(
                    turnOffNotifications = !state.turnOffNotifications
                )
            }
            is SettingsEvents.OnLogoutClicked -> {
                logout(
                    event.context,
                    event.navController
                )
            }
            is SettingsEvents.OnThemeClicked -> {

            }
            is SettingsEvents.OnLanguageClicked -> {

            }
            is SettingsEvents.OnChangePasswordClicked -> {

            }
            is SettingsEvents.OnEditProfileClicked -> {

            }
            is SettingsEvents.OnPrivacyClicked -> {

            }
        }
    }

    private fun logout(
        context: Context,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            val response = userApiRepo.logout(
                TokenManager.getInstance(context).getToken()
            )
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            loadingLogout = it.isLoading
                        )
                        Log.d(TAG, "logout: Loading : ${it.isLoading}")
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            if (it.data.isSuccess) {
                                Log.d(TAG, "logout: Success ${it.data.message}")
                                TokenManager.getInstance(context).clear()
                                navController.backQueue.clear()
                                navController.navigate(Destination.SplashDestination.route)
                            } else {
                                Log.d(TAG, "logout: Success ${it.data.message}")
                            }
                        } else {
                            Log.d(TAG, "logout: Success with empty response")
                        }
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "logout: exception: ${it.data}")
                    }
                }
            }
        }
    }
}