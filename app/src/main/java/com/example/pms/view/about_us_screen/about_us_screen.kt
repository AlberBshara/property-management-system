package com.example.pms.view.about_us_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.about_us_vm.AboutUsScreenVm
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.R
import  com.example.pms.view.animation.AboutUsAnimation as AboutUsAnimationLottie


@Composable
fun AboutUsScreen(
    navController: NavHostController,
    viewModel: AboutUsScreenVm = viewModel()
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopHeader()
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            item {
                AboutUsAnimation()
            }
            item {
                Section(
                    title = stringResource(id = R.string.aboutus_vector),
                    caption = stringResource(id = R.string.about_us)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Section(
                    title = stringResource(id = R.string.our_mission),
                    caption = stringResource(id = R.string.mission)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.thanks),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}


@Composable
private fun TopHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(id = R.string.app_name_without_abbreviation),
                style = MaterialTheme.typography.titleLarge,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .align(Alignment.TopCenter)
            )
        }
        Text(
            text = stringResource(id = R.string.app_version),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }
}

@Composable
private fun AboutUsAnimation() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
    ) {
        AboutUsAnimationLottie(
            isAnimating = true,
            modifier = Modifier
                .fillMaxWidth()
                .size(270.dp)
        )
    }
}

@Composable
private fun Section(
    title: String, caption: String
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        Text(
            text = caption,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}






