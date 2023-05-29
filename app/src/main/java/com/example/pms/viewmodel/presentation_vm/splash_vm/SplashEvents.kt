package com.example.pms.viewmodel.presentation_vm.splash_vm

import android.content.Context
import androidx.navigation.NavHostController

sealed class SplashEvents {
    data class OnAnimationFinished(
        val navController: NavHostController,
        val context : Context
    ) : SplashEvents()
}
