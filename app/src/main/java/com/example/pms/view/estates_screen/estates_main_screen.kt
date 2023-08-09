package com.example.pms.view.estates_screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.lightblue
import com.example.pms.view.chatting_screen.ChattingMainScreen
import com.example.pms.view.estates_screen.estate_home.EstatesHomeScreen
import com.example.pms.view.estates_screen.publish_estate.PublishingEstateScreen
import com.example.pms.view.profile_screen.ProfileScreen
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.estates_vm.EstateMainEvents
import com.example.pms.viewmodel.presentation_vm.estates_vm.EstateMainScreenVM

@SuppressLint("StaticFieldLeak")
private lateinit var context: Context

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun EstatesMainScreen(
    navController: NavHostController,
    viewModel: EstateMainScreenVM = viewModel()

) {


    context = LocalContext.current
    val state = viewModel.state

    Scaffold(

        bottomBar = {
            BottomNavigationState(
                tabs = BottomNavItem.bottomNavTabs,
                onClick = { viewModel.onEvent(EstateMainEvents.CurrentPageRouteChanged(it.route)) })
        }
    ) {

        when (state.currentPageRoute) {
            Destination.EstatesHomeDestination.route -> {
                EstatesHomeScreen(navController = navController)
            }
            Destination.ProfileDestination.route -> {
                ProfileScreen(navController = navController)
            }
            Destination.ChattingMainDestination.route -> {
                ChattingMainScreen(navController = navController)
            }
            Destination.PublishEstateDestination.route -> {
                PublishingEstateScreen()
            }

        }

    }
}


@Composable
private fun BottomNavigationState(
    tabs: List<BottomNavItem>,
    onClick: (BottomNavItem) -> Unit
) {

    val navState = remember { mutableStateOf(1) }

    BottomNavigation(
        backgroundColor = Color.LightGray,
        modifier = Modifier.fillMaxWidth(),

        ) {
        tabs.forEachIndexed { index, tab ->
            BottomNavigationItem(
                selected = navState.value == index,
                onClick = {
                    navState.value = index
                    onClick(tab)
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = tab.icon),
                            contentDescription = null,
                        )
                        Text(
                            text = tab.title,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                    }

                },
                selectedContentColor = lightblue,
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
                Destination.PublishEstateDestination.route
            ),
            BottomNavItem(
                R.drawable.home_ic,
                context.getString(R.string.home),
                Destination.EstatesHomeDestination.route
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