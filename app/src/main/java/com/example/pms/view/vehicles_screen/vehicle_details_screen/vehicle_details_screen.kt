package com.example.pms.view.vehicles_screen.vehicle_details_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm.VehicleDetailsScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pms.R
import com.example.pms.ui.theme.lightBlue
import com.example.pms.ui.theme.orange
import com.example.pms.view.animation.CircularSlider
import com.example.pms.view.utils.RatingBar
import com.example.pms.view.vehicles_screen.vehicle_home.post_card.PagerIndicator
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm.VehicleDetailsEvents
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm.VehicleDetailsState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun VehicleDetailsScreen(
    navController: NavHostController,
    viewModel: VehicleDetailsScreenVM = viewModel()
) {


    val state = viewModel.state

    val pagerState = rememberPagerState(pageCount = state.imagesList.size)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) { index: Int ->
                viewModel.onEvent(VehicleDetailsEvents.OnCurrentImageIndexChanged(pagerState.currentPage))
                AsyncImage(
                    model = state.imagesList[index],
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(
                            shape = RoundedCornerShape(
                                bottomEnd = 16.dp,
                                bottomStart = 16.dp
                            )
                        ),
                    contentScale = ContentScale.FillWidth
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            PagerIndicator(
                current_index = state.currentImageIndex,
                length = state.imagesList.size
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxWidth()
            ) {
                item {
                    VehicleInfo(state)
                    VehicleCase(state)
                }
            }

        }
        BottomItems(
            onBookClinked = {
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .heightIn(max = 50.dp)
                .align(Alignment.BottomCenter),
            price = "3000,000 s.p"
        )
    }
}


@Composable
private fun VehicleInfo(
    state: VehicleDetailsState
) {
    @Composable
    fun ItemOfInfo(
        firstIcon: ImageVector, secondIcon: ImageVector,
        firstString: String, secondString: String
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = firstIcon,
                    contentDescription = null, tint = lightBlue
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = firstString,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = secondIcon,
                    contentDescription = null, tint = lightBlue
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = secondString,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.vehicle_info),
            color = Color.Black,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            textAlign = TextAlign.Start
        )
        ItemOfInfo(
            firstIcon = Icons.Filled.CarRental, secondIcon = Icons.Filled.CarRepair,
            firstString = "BMW", secondString = "BMW 2019 X5 "
        )
        ItemOfInfo(
            firstIcon = Icons.Filled.Sell, secondIcon = Icons.Filled.MergeType,
            firstString = "Sell", secondString = "Automatic"
        )
        ItemOfInfo(
            firstIcon = Icons.Filled.Colorize, secondIcon = Icons.Filled.LocalGasStation,
            firstString = "Blue", secondString = "Gasoline"
        )

    }
}


@Composable
private fun VehicleCase(
    state: VehicleDetailsState
) {
    Text(
        text = stringResource(id = R.string.vehicle_state),
        color = Color.Black,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textAlign = TextAlign.Start
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            CircularSlider(
                modifier = Modifier.size(180.dp),
                thumbColor = lightBlue,
                progressColor = lightBlue,
                defaultValue = 1000f,
                constant = true
            ) {}
            Text(
                text = "50,000" + stringResource(id = R.string.km),
                style = MaterialTheme.typography.caption,
                color = Color.Black
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(end = 10.dp, bottom = 10.dp)
            ) {
                Text(//text = if (state.usedVehicle) stringResource(id = R.string.used_vehicle)
//                     else stringResource(id = R.string.new_vehicle),
                    text = "New",
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
            RatingBar(rating = 3, size = 18.dp)
        }
    }
}

@Composable
private fun BottomItems(
    modifier: Modifier = Modifier,
    onBookClinked: () -> Unit,
    price: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.price),
                color = Color.Gray,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Start
            )
            Text(
                text = price,
                color = Color.Black,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            onClick = {
                onBookClinked()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = lightBlue,
                disabledBackgroundColor = lightBlue
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Book",
                color = Color.White,
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center
            )
        }
    }
}