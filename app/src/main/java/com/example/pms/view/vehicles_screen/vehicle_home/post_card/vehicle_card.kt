package com.example.pms.view.vehicles_screen.vehicle_home.post_card

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.postcard_vehicle_vm.PostVehicleVM
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pms.ui.theme.orange
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.postcard_vehicle_vm.PostVehicleEvents


private const val NUMBER_OF_IMAGES : Int = 3

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun VehicleCard(
    navController: NavHostController,
    onClick: () -> Unit,
    viewModel: PostVehicleVM = viewModel()
) {

    val state = viewModel.state

    val pagerState = rememberPagerState(pageCount = NUMBER_OF_IMAGES)

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
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.padding(14.dp)
            ) { current_image_index ->
                state.currentImageIndex = current_image_index
                Image(
                    painter = painterResource(id = state.images[current_image_index]),
                    contentDescription = "Car Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            PagerIndicator(current_index = state.currentImageIndex ,
                length = NUMBER_OF_IMAGES)

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.carName,
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
                       text = if (state.usedVehicle) stringResource(id = R.string.used_vehicle)
                       else stringResource(id = R.string.new_vehicle),
                       color = Color.Black,
                       style = MaterialTheme.typography.caption,
                       modifier = Modifier
                           .padding(start = 10.dp, end = 10.dp),
                   )
                   Icon(painter = painterResource(id = R.drawable.circle_true_ic),
                       contentDescription = null,
                   tint = orange)
               }
            }
           Row (
               modifier = Modifier
                   .fillMaxWidth(),
               horizontalArrangement = Arrangement.Start,
               verticalAlignment = Alignment.CenterVertically
                   ) {
               Text(
                   text = state.carPrice,
                   style = MaterialTheme.typography.h4,
                   modifier = Modifier
                       .padding(start = 10.dp, end = 10.dp)
               )
               Text(text = state.previousPrice,
               color = Color.Green,
               style = MaterialTheme.typography.caption,
               modifier = Modifier
                   .padding(start= 10.dp, end= 10.dp),
                   textDecoration = TextDecoration.LineThrough
               )
           }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                IconButton(
                    onClick = {
                        viewModel.onEvent(PostVehicleEvents.LoveChanged)
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.love_icon),
                        contentDescription = "",
                        tint = if (state.loved) Color.Red else Color.Gray
                    )
                }

                Button(
                    onClick = {
                        viewModel.onEvent(PostVehicleEvents.ViewMore(navController))
                        onClick()
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

