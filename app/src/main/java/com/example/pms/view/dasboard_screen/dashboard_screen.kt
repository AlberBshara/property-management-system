package com.example.pms.view.dasboard_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.background1
import com.example.pms.viewmodel.presentation_vm.dashboard_vm.DashboardScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.viewmodel.presentation_vm.dashboard_vm.DashboardEvents

@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardScreenVM = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background1),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
                .weight(0.2f)
        ) {
            Text(
                text = stringResource(id = R.string.pm),
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            )

            Text(
                text = stringResource(id = R.string.system),
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            )
        }


        Row(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(0.5f)
        ) {


            Column {
                GridCell(R.drawable.car_vector, R.string.car_vector, onClick = {
                    viewModel.onEvent(DashboardEvents.OnVehicleClicked(navController))
                })
                GridCell(R.drawable.settings_vector, R.string.settings_vector, onClick = {})
                GridCell(R.drawable.profile_vector, R.string.profile_vector, onClick = {})
            }

            Column {
                GridCell(R.drawable.house_vector, R.string.estate_vector, onClick = {})
                GridCell(R.drawable.suggestion_vector, R.string.suggestion_vector, onClick = {})
                GridCell(R.drawable.aboutus_vector, R.string.aboutus_vector, onClick = {})
            }
        }


    }


}