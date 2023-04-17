package com.example.pms.viewmodel.destinations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pms.view.login_screen.LoginScreen
import com.example.pms.view.regisiter_screen.RegisterScreen
import com.example.pms.view.splashscreen.SplashScreen


sealed class Destination(
    val route: String
) {
    object SplashDestination : Destination("splash-screen-destination")
    object LoginDestination : Destination("login-screen-destination")
    object RegisterDestination : Destination("register-screen-destination")
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
            RegisterScreen(navController)
        }

    }

}
