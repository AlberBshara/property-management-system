package com.example.pms.viewmodel.destinations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pms.view.chatting_screen.ChattingMainScreen
import com.example.pms.view.chatting_screen.messages_screen.MessagesScreen
import com.example.pms.view.dasboard_screen.DashboardScreen
import com.example.pms.view.forgot_password_screen.ForgetPassword
import com.example.pms.view.forgot_password_screen.ResetPasswordScreen
import com.example.pms.view.login_screen.LoginScreen
import com.example.pms.view.profile_screen.ProfileScreen
import com.example.pms.view.profile_screen.edit_profile_info.EditScreen
import com.example.pms.view.profile_screen.edit_profile_info.edit_password.EditPasswordScreen
import com.example.pms.view.profile_screen.profile_helper_screen.ProfileHelperScreen
import com.example.pms.view.regisiter_screen.RegisterScreen
import com.example.pms.view.settings_screen.SettingsScreen
import com.example.pms.view.splashscreen.SplashScreen
import com.example.pms.view.suggesstions.SuggestionMainScreen
import com.example.pms.view.vehicles_screen.VehiclesMainScreen
import com.example.pms.view.vehicles_screen.publish_car.PublishingCarScreen
import com.example.pms.view.vehicles_screen.vehicle_details_screen.VehicleDetailsScreen
import com.example.pms.view.vehicles_screen.vehicle_home.VehiclesHomeScreen


sealed class Destination(
    val route: String
) {
    object SplashDestination : Destination("splash-screen-destination")
    object LoginDestination : Destination("login-screen-destination")
    object RegisterDestination : Destination("register-screen-destination")
    object ForgetPasswordDestination : Destination("forget-password-destination")
    object DashboardDestination : Destination("dashboard-destination")
    object VehiclesMainDestination : Destination("vehicles-destination")
    object ProfileDestination : Destination("profile-screen-destination")
    object PublishCarDestination : Destination("publish-car-screen-destination")
    object VehiclesHomeDestination : Destination("vehicles-home-screen-destination")
    object ChattingMainDestination : Destination("chatting-main-screen-destination")
    object MessagesDestination : Destination("messages-screen-destination")
    object VehicleDetailsDestination : Destination("vehicle_details-screen-destination")
    object SettingsDestination : Destination("settings-screen-destination")
    object ResetPasswordScreen : Destination("reset_password_screen-destination")
    object SuggestionsDestination : Destination("suggestions-main-screen-destination")
    object EditProfileInfoDestination : Destination("edit_profile_info_screen_destination")
    object EditPasswordScreen : Destination("edit_password_screen_destination")
    object ProfileHelperScreen : Destination("profile-helper-screen-destination") {
        const val FROM_KEY: String = "from"
        const val FROM_FAV_CLICKED: String = "favorite"
        const val FROM_MY_POST_CLICKED: String = "my posts"
    }

    companion object {
        const val CAR_ID_KEY: String = "carId"
        const val ESTATE_ID_KEY: String = "estateId"
    }
}


@RequiresApi(Build.VERSION_CODES.M)
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
            RegisterScreen(
                navController = navController,
                pageNumber ?: RegisterPages.registerPage1
            )
        }

        composable(route = Destination.ForgetPasswordDestination.route) {
            ForgetPassword(navController)
        }
        composable(Destination.DashboardDestination.route) {
            DashboardScreen(navController)
        }
        composable(Destination.VehiclesMainDestination.route) {
            VehiclesMainScreen(navController)
        }
        composable(Destination.ProfileDestination.route) {
            ProfileScreen(navController)
        }
        composable(Destination.PublishCarDestination.route) {
            PublishingCarScreen(navController)
        }
        composable(Destination.VehiclesHomeDestination.route) {
            VehiclesHomeScreen(navController)
        }
        composable(Destination.ChattingMainDestination.route) {
            ChattingMainScreen(navController)
        }
        composable(Destination.MessagesDestination.route) {
            MessagesScreen(navController)
        }
        composable(Destination.VehicleDetailsDestination.route) {
            val carId =
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>(Destination.CAR_ID_KEY)
            VehicleDetailsScreen(navController, carId = carId ?: -1)
        }
        composable(Destination.SettingsDestination.route) {
            SettingsScreen(navController)
        }
        composable(Destination.ResetPasswordScreen.route) {
            ResetPasswordScreen(navController = navController)
        }
        composable(Destination.SuggestionsDestination.route) {
            SuggestionMainScreen(navController)
        }
        composable(Destination.EditProfileInfoDestination.route) {
            EditScreen(navController = navController)
        }
        composable(Destination.EditPasswordScreen.route) {
            EditPasswordScreen(navHostController = navController)
        }
        composable(Destination.ProfileHelperScreen.route) {
            val fromValue =
                navController.previousBackStackEntry?.savedStateHandle
                    ?.get<String>(
                        Destination.ProfileHelperScreen.FROM_KEY
                    )
            ProfileHelperScreen(
                navController = navController, from = fromValue
                    ?: Destination.ProfileHelperScreen.FROM_FAV_CLICKED
            )
        }
    }

}
