package com.example.pms.view.profile_screen.profile_helper_screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pms.R
import com.example.pms.ui.theme.orange
import com.example.pms.view.vehicles_screen.vehicle_home.NUMBER_OF_IMAGES
import com.example.pms.view.vehicles_screen.vehicle_home.post_card.PagerIndicator
import com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm.ProfileHelperEvents
import com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm.ProfileHelperScreenVM
import com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm.ProfileHelperState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ListVehicleContent(
    state: ProfileHelperState,
    viewModel: ProfileHelperScreenVM,
    context: Context,
    navController: NavHostController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(state.postsList) { index, item ->
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
                                text = "${item.vehicleData.price.toInt()} S.P",
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(
                                        ProfileHelperEvents.LikeClicked(
                                            ProfileHelperScreenVM.VEHICLE,
                                            item.vehicleData.id,
                                            context
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .padding(start = 10.dp, bottom = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.love_icon),
                                    contentDescription = "",
                                    tint = if (item.liked) Color.Red else Color.Gray
                                )
                            }
                            Button(
                                onClick = {
                                    viewModel.onEvent(
                                        ProfileHelperEvents.OnViewMoreClicked(
                                            type = ProfileHelperScreenVM.VEHICLE,
                                            typeId = item.vehicleData.id,
                                            navController = navController
                                        )
                                    )
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
        }
    }
}
