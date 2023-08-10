package com.example.pms.viewmodel.presentation_vm.settings_vm

import android.content.Context
import android.widget.Toast
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
import com.example.pms.R
import com.example.pms.viewmodel.preferences.UserPreferences
import com.example.pms.viewmodel.presentation_vm.settings_vm.settings_manager.SettingsManager

class SettingsScreenVM(
    private val userApiRepo: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    var state by mutableStateOf(SettingsState())

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
                changeLanguage(event.context, event.selectedLanguage)
            }
            is SettingsEvents.OnChangePasswordClicked -> {
                event.navController.navigate(Destination.EditPasswordScreen.route)
            }
            is SettingsEvents.OnEditProfileClicked -> {
                event.navController.navigate(Destination.EditProfileInfoDestination.route)
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
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            if (it.data.isSuccess) {
                                TokenManager.getInstance(context).clear()
                                UserPreferences.clear(context)
                                navController.backQueue.clear()
                                navController.navigate(Destination.SplashDestination.route)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            context, context.getString(R.string.try_later),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun changeLanguage(
        context: Context,
        selectedLanguage: String
    ) {
        val settingsManager = SettingsManager(context)
        if (selectedLanguage != settingsManager.currentLanguage()) {
            settingsManager.changeLanguage(selectedLanguage)
            settingsManager.restartApp(context)
        } else {
            Toast.makeText(
                context, context.getString(R.string.same_language),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}