package com.example.pms.view.regisiter_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.transparent_p
import com.example.pms.view.animation.RotateImage
import com.example.pms.view.regisiter_screen.pages.RegisterPag3
import com.example.pms.view.regisiter_screen.pages.RegisterPage1
import com.example.pms.view.regisiter_screen.pages.RegisterPage2
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.ui.theme.background1


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    pageNumber: Int = RegisterPages.registerPage1,
    viewModel : RegisterScreenVM = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background1),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (pageNumber) {
            RegisterPages.registerPage1 -> {
                   RotateImage(
                       image = R.drawable.logo,
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(10.dp)
                           .weight(0.2f)
                   )
            }
            else -> {
                Image(
                    painter = painterResource(
                        id = R.drawable.logo
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .weight(0.2f)
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 50.dp, bottom = 50.dp)
                .weight(0.6f),
            backgroundColor = transparent_p,
            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {
                when (pageNumber) {
                    RegisterPages.registerPage1 -> RegisterPage1(navController = navController)
                    RegisterPages.registerPage2 -> RegisterPage2(navController = navController)
                    RegisterPages.registerPage3 -> RegisterPag3(navController = navController)
                }
            }
        }
    }
}