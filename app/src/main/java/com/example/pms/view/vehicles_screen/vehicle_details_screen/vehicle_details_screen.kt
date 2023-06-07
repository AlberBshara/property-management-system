package com.example.pms.view.vehicles_screen.vehicle_details_screen


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm.VehicleDetailsScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pms.R
import com.example.pms.ui.theme.*
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
    val context = LocalContext.current
    viewModel.onEvent(VehicleDetailsEvents.OnStart(context))
    val state = viewModel.state

    if (state.isLoading) {
        ShimmerViewMoreDetails()
    } else {
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

                if (state.imagesList.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) { index: Int ->
                            viewModel.onEvent(
                                VehicleDetailsEvents.OnCurrentImageIndexChanged(
                                    pagerState.currentPage
                                )
                            )
                            AsyncImage(
                                model = state.imagesList[index].imageUrl,
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
                        FloatingActionButton(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            backgroundColor = transparentGray,
                            onClick = {
                                viewModel.onEvent(VehicleDetailsEvents.OnShareClicked(context))
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                        }
                    }
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
                        ContactInfo(state,
                            onCallPhoneListener = {
                                viewModel.onEvent(
                                    VehicleDetailsEvents.OnCallPhoneClicked(
                                        it,
                                        context
                                    )
                                )
                            }
                        )
                        MoreInfo(state)
                        OwnerCard(state,
                            onVisitProfileListener = {

                            },
                            onChattingListener = {

                            })
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
                price = state.price.toString() + " s.p "
            )
        }
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
            firstString = state.brand, secondString = state.secondBrand
        )
        ItemOfInfo(
            firstIcon = Icons.Filled.Sell, secondIcon = Icons.Filled.MergeType,
            firstString = state.operationType, secondString = state.transmissionType
        )
        ItemOfInfo(
            firstIcon = Icons.Filled.Colorize, secondIcon = Icons.Filled.LocalGasStation,
            firstString = state.color, secondString = state.fuelType
        )
        ItemOfInfo(
            firstIcon = Icons.Filled.Engineering,
            secondIcon = Icons.Filled.Timelapse,
            firstString = state.drivingForce, secondString = state.yearOfManufacture.toString()
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
        verticalAlignment = Alignment.CenterVertically,

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
                defaultValue = state.kilometer.toFloat(),
                constant = true
            ) {}
            Text(
                text = state.kilometer.toString() + stringResource(id = R.string.km),
                style = MaterialTheme.typography.caption,
                color = Color.Black
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .background(color = transparent_p, shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(end = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    //text = if (state.usedVehicle) stringResource(id = R.string.used_vehicle)
                    // else stringResource(id = R.string.new_vehicle),
                    text = state.condition,
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
private fun ContactInfo(
    state: VehicleDetailsState,
    onCallPhoneListener: (phoneNumber: String) -> Unit
) {
    Text(
        text = stringResource(id = R.string.contact_info),
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
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.MyLocation,
            contentDescription = null,
            tint = lightBlue,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(
            text = state.location,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle2
        )
    }
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = null,
                tint = lightBlue,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(
                text = "+963-947424243",
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle2
            )
        }
        IconButton(onClick = {
            onCallPhoneListener("+963-947424243")
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_phone),
                contentDescription = null,
                tint = Color.Green
            )
        }
    }
}

@Composable
private fun OwnerCard(
    state: VehicleDetailsState,
    onVisitProfileListener: () -> Unit,
    onChattingListener: () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = lightBlue),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = stringResource(id = R.string.owner),
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.person_profile),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(50.dp)
                            .border(
                                border = BorderStroke(
                                    2.dp,
                                    Brush.sweepGradient(rainbowColors)
                                ),
                                shape = CircleShape
                            )
                            .clip(shape = CircleShape)
                    )
                    Text(
                        text = "Alber Bshara",
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                    )
                }
                IconButton(onClick = {
                    onChattingListener()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Message,
                        contentDescription = null,
                        tint = darkYellow
                    )
                }

            }
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.visit_profile),
                    textAlign = TextAlign.End,
                    color = lightBlue,
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            onVisitProfileListener()
                        },
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            }
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


@Composable
private fun MoreInfo(
    state: VehicleDetailsState
) {
    Text(
        text = stringResource(id = R.string.additional_info_vehicle),
        color = Color.Black,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textAlign = TextAlign.Start
    )

    OutlinedTextField(
        value = state.description,
        onValueChange = {},
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = transparentGray,
            focusedBorderColor = lightBlue,
            unfocusedBorderColor = transparentGray,
            cursorColor = lightBlue
        ),
        shape = RoundedCornerShape(10.dp),
        singleLine = false,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.description_ic),
                contentDescription = null,
                tint = lightBlue
            )
        },
        readOnly = true
    )
}

