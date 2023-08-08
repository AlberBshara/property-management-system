package com.example.pms


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pms.ui.theme.PMSTheme
import com.example.pms.viewmodel.destinations.PmsNavHost
import com.example.pms.viewmodel.presentation_vm.settings_vm.settings_manager.SettingsManager


@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PMSTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    //init the navigation component:
                    navController = rememberNavController()
                    PmsNavHost(navController = navController)

                    //set the Settings to the app:
                    val settingsManager = SettingsManager(this.applicationContext)
                    settingsManager.setDefaultSettings(this.applicationContext)
                }
            }
        }
    }
}

