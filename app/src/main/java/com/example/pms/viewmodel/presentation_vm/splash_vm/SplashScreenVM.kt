package com.example.pms.viewmodel.presentation_vm.splash_vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class SplashScreenVM : ViewModel() {

    fun onEvent(event: SplashEvents) {
        when (event) {
            is SplashEvents.OnAnimationFinished -> {
                onAnimationFinished(event.navController, event.context)
            }
        }
    }

    private fun onAnimationFinished(
        navController: NavHostController,
        context: Context
    ) {
        viewModelScope.launch {
            if (TokenManager.getInstance(context).exits()
                && TokenManager.getInstance(context).isVerified()
                && TokenManager.getInstance(context).isCompleted()
            ) {
                navController.popBackStack()
                navController.navigate(Destination.DashboardDestination.route)
            } else {
                navController.popBackStack()
                navController.navigate(Destination.LoginDestination.route)
            }
        }
    }
}