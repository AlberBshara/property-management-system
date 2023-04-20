package com.example.pms.viewmodel.destinations

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pms.view.forgot_password_screen.ForgetPassword
import com.example.pms.view.login_screen.LoginScreen
import com.example.pms.view.regisiter_screen.RegisterScreen
import com.example.pms.view.splashscreen.SplashScreen


private const val TAG: String = "Destination.KClass"

sealed class Destination(
    val route: String
) {
    object SplashDestination : Destination("splash-screen-destination")
    object LoginDestination : Destination("login-screen-destination")
    object RegisterDestination : Destination("register-screen-destination")
    object ForgetPasswordDestination : Destination("forget-password-screen")
}


@Composable
fun PmsNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Destination.SplashDestination.route
    ) {
        composable(route = Destination.SplashDestination.route) {
            SplashScreen(navController)
        }

        composable(route = Destination.LoginDestination.route) {
            LoginScreen(navController)
        }

        composable(route = Destination.RegisterDestination.route) {
            val pageNumber =
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>(RegisterPages.KEY)

            Log.d(TAG, "to page number of: $pageNumber")

            RegisterScreen(
                navController = navController,
                pageNumber ?: RegisterPages.registerPage1
            )
        }

        composable(route = Destination.ForgetPasswordDestination.route) {
            ForgetPassword(navController)
        }
    }

}
