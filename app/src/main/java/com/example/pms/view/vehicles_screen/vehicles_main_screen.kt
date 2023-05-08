package com.example.pms.view.vehicles_screen


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.VehiclesMainScreenVm
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pms.R
import com.example.pms.view.chatting_screen.ChattingMainScreen
import com.example.pms.view.profile_screen.ProfileScreen
import com.example.pms.view.vehicles_screen.publish_car.PublishingCarScreen
import com.example.pms.view.vehicles_screen.vehicle_home.VehiclesHomeScreen
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.VehicleMainEvents


@SuppressLint("StaticFieldLeak")
private lateinit var context: Context

@Composable
fun VehiclesMainScreen(
    navController: NavHostController,
    viewModel: VehiclesMainScreenVm = viewModel()
) {

    context = LocalContext.current

    val state = viewModel.state


    Scaffold(
        bottomBar = {
            BottomNavigationVehicle(
                tabs = BottomNavItem.bottomNavTabs,
                navController = navController,
                onItemClick = {
                    viewModel.onEvent(VehicleMainEvents.CurrentPageRouteChanged(it.route))
                })
        }
    ) {
        when (state.currentPageRoute) {
            Destination.VehiclesHomeDestination.route -> {
                VehiclesHomeScreen(navController)
            }
            Destination.ProfileDestination.route -> {
                ProfileScreen(navController)
            }
            Destination.PublishCarDestination.route -> {
                PublishingCarScreen(navController)
            }
            Destination.ChattingMainDestination.route -> {
                ChattingMainScreen(navController)
            }

        }
    }
}


@Composable
private fun BottomNavigationVehicle(
    tabs: List<BottomNavItem>,
    navController: NavHostController,
    onItemClick: (BottomNavItem) -> Unit
) {

    val backEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        backgroundColor = Color.LightGray,
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(25.dp))
    ) {
        tabs.forEach {
            val selected = (it.route == backEntry.value?.destination?.route)
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(it) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = it.icon),
                            contentDescription = null,
                            tint = if (selected) Color.Green else Color.Black
                        )
                        Text(
                            text = it.title,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                    }
                },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Black
            )
        }

    }

}

private data class BottomNavItem(
    val icon: Int,
    val title: String,
    val route: String
) {
    companion object {
        val bottomNavTabs = listOf(
            BottomNavItem(
                R.drawable.upload_ic,
                context.getString(R.string.sell),
                Destination.PublishCarDestination.route
            ),
            BottomNavItem(
                R.drawable.home_ic,
                context.getString(R.string.home),
                Destination.VehiclesHomeDestination.route
            ),
            BottomNavItem(
                R.drawable.profile_ic,
                context.getString(R.string.profile),
                Destination.ProfileDestination.route
            ),
            BottomNavItem(
                R.drawable.chatting_ic,
                context.getString(R.string.chatting),
                Destination.ChattingMainDestination.route
            )
        )
    }
}

