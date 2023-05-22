package com.example.pms.view.vehicles_screen.vehicle_home

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.view.vehicles_screen.vehicle_home.post_card.VehicleCard
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.ui.theme.noColor
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeEvents
import com.example.pms.R
import com.example.pms.view.animation.ShowShimmerEffect
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeConstants
import kotlinx.coroutines.delay

@Composable
fun VehiclesHomeScreen(
    navController: NavHostController,
    viewModel: VehicleHomeVM = viewModel()
) {

    val state = viewModel.state


    LaunchedEffect(key1 = true) {
        delay(5000)
        viewModel.onEvent(VehicleHomeEvents.LoadingCaseChanged)
    }

    if (state.isLoading) {
        ShowShimmerEffect()
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 53.dp)
        ) {

            AnimatedVisibility(
                visible = state.showSearchBar,
                enter = slideInHorizontally(),
            ) {
                SearchBar(
                    query = state.query,
                    onCancelListener = {
                        viewModel.onEvent(VehicleHomeEvents.ShowSearchBar)
                    },
                    onQueryListener = {
                        viewModel.onEvent(VehicleHomeEvents.SearchQueryChanged(it))
                    }
                )
            }
            if (!state.showSearchBar) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(color = noColor),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                                .padding(start = 10.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = stringResource(id = R.string.app_name_without_abbreviation),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black,
                        )
                    }
                    IconButton(onClick = {
                        viewModel.onEvent(VehicleHomeEvents.ShowSearchBar)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }

            FilteringRow(titles =
            VehicleHomeConstants.listOfFiltering,
                onSelectedItem = {
                    viewModel.onEvent(VehicleHomeEvents.ShowDropDownFilter(it))
                })

            AnimatedVisibility(
                visible = state.showDropDownFilter,
                enter = slideInVertically()
            ) {

                DropdownMenu(
                    expanded = state.showDropDownFilter,
                    onDismissRequest = {
                        viewModel.onEvent(VehicleHomeEvents.ShowDropDownFilter(state.filterId))
                    }) {
                    VehicleHomeConstants.listOfFiltering[state.filterId].items.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onEvent(VehicleHomeEvents.FilterTypeChanged(item))
                            }) {
                            Text(
                                text = item,
                                modifier = Modifier.padding(2.dp)
                            )
                        }
                    }
                }
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(5) {
                    VehicleCard(navController = navController, onClick = { /*TODO*/ })
                }
            }
        }
    }

}

