package com.example.pms.viewmodel.presentation_vm.settings_vm

import android.content.Context
import androidx.navigation.NavHostController

sealed class SettingsEvents {

    object OnNotificationsCaseChanged : SettingsEvents()

    data class OnLogoutClicked(
        val context: Context,
        val navController: NavHostController
    ) : SettingsEvents()

    data class OnEditProfileClicked(
        val navController: NavHostController
    ) : SettingsEvents()

    data class OnChangePasswordClicked(
        val navController: NavHostController
    ) : SettingsEvents()

    data class OnPrivacyClicked(
        val navController: NavHostController
    ) : SettingsEvents()

    data class OnLanguageClicked(
        val navController: NavHostController,
        val context: Context,
        val selectedLanguage: String
    ) : SettingsEvents()

    data class OnThemeClicked(
        val navController: NavHostController
    ) : SettingsEvents()

}
