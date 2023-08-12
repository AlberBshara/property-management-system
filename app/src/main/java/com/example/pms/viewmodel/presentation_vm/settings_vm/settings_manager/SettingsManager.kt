package com.example.pms.viewmodel.presentation_vm.settings_vm.settings_manager

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.example.pms.viewmodel.preferences.PMSSharedPreferenceHelper
import java.util.Locale

class SettingsManager(
    context: Context
) {
    private val sharedPreferences = PMSSharedPreferenceHelper(context)

    fun changeLanguage(language: String) {
        sharedPreferences.setData(PMSSharedPreferenceHelper.LANGUAGE, language)
    }

    fun currentLanguage(): String =
        sharedPreferences.getData(PMSSharedPreferenceHelper.LANGUAGE)

    fun restartApp(context: Context) {
        val intent =
            context.applicationContext.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * the following function will be called after any configuration's
     * change in the application:
     */
    fun setDefaultSettings(context: Context) {
        try {
            val locale = Locale(currentLanguage())
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.setLocale(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.createConfigurationContext(configuration)
            } else {
                context.resources.updateConfiguration(
                    configuration,
                    context.resources.displayMetrics
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun changeLanguageForTiramisu(
        context: Context,
        selectedLanguage: String
    ) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.getSystemService(LocaleManager::class.java)
                    .applicationLocales = LocaleList.forLanguageTags(selectedLanguage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}