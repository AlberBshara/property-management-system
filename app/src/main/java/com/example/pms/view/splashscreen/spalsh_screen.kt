package com.example.pms.view.splashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.pms.ui.theme.background1
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.R
import com.example.pms.view.animation.ScaleInImageAnimation

@Composable
fun SplashScreen(
    navController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(background1)
    ) {
        ScaleInImageAnimation(
            image = painterResource(id = R.drawable.logo_background),
            onAnimationFinished = {
                navController.popBackStack()
                navController.navigate(Destination.LoginDestination.route)
            }
        )
    }
}

