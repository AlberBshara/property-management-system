package com.example.pms.view.vehicles_screen.vehicle_home

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeVM
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pms.ui.theme.noColor
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeEvents
import com.example.pms.R
import com.example.pms.ui.theme.orange
import com.example.pms.view.animation.ProgressAnimatedBar
import com.example.pms.view.animation.ShowShimmerEffect
import com.example.pms.view.utils.RefreshScreen
import com.example.pms.view.vehicles_screen.vehicle_home.post_card.PagerIndicator
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeConstants
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

private const val NUMBER_OF_IMAGES: Int = 3

@Composable
fun VehiclesHomeScreen(
    navController: NavHostController,
    viewModel: VehicleHomeVM = viewModel()
) {

    val context = LocalContext.current
    viewModel.onEvent(VehicleHomeEvents.OnStart(context))
    val state = viewModel.state


   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center,
       modifier = Modifier
           .fillMaxSize()
   ) {
       AnimatedVisibility(
           visible = state.showSearchBar,
           enter = slideInHorizontally(),
       ) {
           SearchBar(
               query = state.query,
               onCancelListener = {
                   viewModel.onEvent(VehicleHomeEvents.OnCancelSearch(context))
               },
               onQueryListener = {
                   viewModel.onEvent(VehicleHomeEvents.SearchQueryChanged(it))
               } ,
               onDone = {
                   viewModel.onEvent(VehicleHomeEvents.OnDoneSearchQuery(it, context))
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
       if (state.isLoading) {
           ShowShimmerEffect()
       }
       else {
           RefreshScreen(needRefresh = state.needRefresh) {
               viewModel.onEvent(VehicleHomeEvents.OnNeedRefresh(context))
           }
           if (!state.needRefresh) {
               Column(
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center,
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(bottom = 53.dp)
               ) {
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
                   ListContent(state = state, viewModel = viewModel, context = context, navController = navController)
               }
           }
       }
   }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ListContent(
    state: VehicleHomeState,
    viewModel: VehicleHomeVM,
    context: Context ,
    navController: NavHostController
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(state.postsDataList) { index, item ->
                viewModel.vehiclesListScrollPosition = index
                if ((index + 1) >= (viewModel.pageNumber.value * VehicleHomeVM.PAGE_SIZE)) {
                    viewModel.onEvent(VehicleHomeEvents.Paginate(context))
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 5.dp
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        val pagerState: PagerState =
                            if (item.images.size > NUMBER_OF_IMAGES) {
                                rememberPagerState(pageCount = NUMBER_OF_IMAGES)
                            } else {
                                rememberPagerState(pageCount = item.images.size)
                            }
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.padding(14.dp)
                        ) { current_image_index ->
                            if (current_image_index < item.images.size) {
                                AsyncImage(
                                    model = item.images[current_image_index].imageUrl,
                                    contentDescription = "Car Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(shape = RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .background(color = Color.White)
                                        .clip(shape = RoundedCornerShape(16.dp)),
                                )
                            }
                        }
                        PagerIndicator(
                            current_index = pagerState.currentPage,
                            length = if (item.images.size > NUMBER_OF_IMAGES) NUMBER_OF_IMAGES else item.images.size
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.vehicleData.brand,
                                modifier = Modifier
                                    .padding(10.dp),
                                style = MaterialTheme.typography.subtitle1
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.padding(end = 10.dp)
                            ) {
                                Text(
                                    text = item.vehicleData.status,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.caption,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp),
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.circle_true_ic),
                                    contentDescription = null,
                                    tint = orange
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text ="${item.vehicleData.price} S.P",
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                            )
//                                Text(text = state.previousPrice,
//                                    color = Color.Green,
//                                    style = MaterialTheme.typography.caption,
//                                    modifier = Modifier
//                                        .padding(start= 10.dp, end= 10.dp),
//                                    textDecoration = TextDecoration.LineThrough
//                                )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = {
                                    //  viewModel.onEvent(PostVehicleEvents.LoveChanged)
                                },
                                modifier = Modifier
                                    .padding(start = 10.dp, bottom = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.love_icon),
                                    contentDescription = "",
                                    tint = Color.Gray
                                    //if (it.loved) Color.Red else Color.Gray
                                )
                            }
                            Button(
                                onClick = {
                                   viewModel.onEvent(VehicleHomeEvents.OnViewMoreClicked(
                                       carId = item.vehicleData.id,
                                       navController = navController
                                   ))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 26.dp, bottom = 10.dp),
                                colors = ButtonDefaults.buttonColors(Color.LightGray)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.view_more),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
            item {
                if (state.gettingNewPosts) {
                    ProgressAnimatedBar(
                        isLoading = true,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
    }

}
