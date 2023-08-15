package com.example.pms.view.estates_screen.estate_viewMore


import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pms.R
import com.example.pms.ui.theme.*
import com.example.pms.view.estates_screen.PagerIndicator
import com.example.pms.view.vehicles_screen.vehicle_details_screen.ShimmerViewMoreDetails
import com.example.pms.viewmodel.presentation_vm.estates_vm.view_more_estate_vm.RoomsViewMore
import com.example.pms.viewmodel.presentation_vm.estates_vm.view_more_estate_vm.ViewMoreEstateEvents
import com.example.pms.viewmodel.presentation_vm.estates_vm.view_more_estate_vm.ViewMoreEstateScreenVM
import com.example.pms.viewmodel.presentation_vm.estates_vm.view_more_estate_vm.ViewMoreEstateStates
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewMoreScreenEstate(
    navHostController: NavHostController,
    viewModel: ViewMoreEstateScreenVM = androidx.lifecycle.viewmodel.compose.viewModel(),
    estate_id: Int = -1
) {
    val context = LocalContext.current
    viewModel.onEvent(
        ViewMoreEstateEvents.OnGetDataFromServer(
            context = context,
            estateId = estate_id
        )
    )
    val state = viewModel.state

    if (state.isLoading) {
        ShimmerViewMoreDetails()
    } else {
        val pagerState = rememberPagerState(pageCount = state.imagesList.size, initialPage = 0)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Column {
                if (state.imagesList.isNotEmpty()) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .onFocusChanged { }
                    ) { current_image_index ->
                        AsyncImage(
                            model = state.imagesList[current_image_index].imageUrl,
                            contentDescription = "Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                            //contentScale = ContentScale.FillWidth
                        )
                        viewModel.onEvent(ViewMoreEstateEvents.ChangeImage(indexOfImage = pagerState.currentPage))
                    }
                    Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(
                                            ViewMoreEstateEvents.OnLikeClicked(
                                                context,
                                                estate_id
                                            )
                                        )
                                    },
                                ) {
                                    if (state.loved) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.love_icon),
                                            contentDescription = "",
                                            tint = Color.Red,
                                            modifier = Modifier.size(25.dp)
                                        )
                                    } else {
                                        Icon(
                                            painter = painterResource(id = R.drawable.love_icon2),
                                            contentDescription = "",
                                            tint = darkRed,
                                            modifier = Modifier.size(25.dp)
                                        )
                                    }
                                }

                                Text(text = state.numberOfLikes.toString(), fontSize = 12.sp)

                            }
                            PagerIndicator(
                                current_index = pagerState.currentPage,
                                state.imagesList.size
                            )
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.share_ic),
                                    contentDescription = null,
                                    tint = lightblue,
                                    modifier = Modifier.clickable {
                                        viewModel.onEvent(
                                            ViewMoreEstateEvents.OnShareClicked(
                                                context
                                            )
                                        )
                                    }
                                )
                            }

                        }
                    }
                }
                Divider(
                    modifier = Modifier.padding(bottom = 10.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.operationType == "sell") {
                            Text(
                                text = state.price.toString() + " $",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 5.dp)
                            )
                        } else {
                            Text(
                                text = state.price.toString(),
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 5.dp)
                            )
                            Text(
                                text = " $/month",
                                fontSize = 15.sp
                            )
                        }
                    }
                    RatingBar(size = 18.dp, rating = state.rate)
                }
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.End) {
                    Text(
                        text = stringResource(id = R.string.add_rate),
                        color = lightblue,
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .clickable {
                                viewModel.onEvent(ViewMoreEstateEvents.OnChangeShowRateScreen)

                            },
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.location_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp),
                        tint = Color.Black

                    )
                    Text(
                        text = state.governorate,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {

                    RoomsViewMore(state = state).listOfRooms.forEachIndexed { _, oneRoom ->

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Row {
                                Icon(
                                    painter = painterResource(id = oneRoom.image),
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                                Text(
                                    text = oneRoom.number.toString(),
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }
                            Text(text = oneRoom.name, color = Color.Gray)

                        }


                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
//table
                MyTable(state)

//more info
                Text(
                    text = stringResource(id = R.string.additional_information_estate),
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
                        focusedBorderColor = lightblue,
                        unfocusedBorderColor = transparentGray,
                        cursorColor = lightblue
                    ),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = false,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.description_ic),
                            contentDescription = null,
                            tint = lightblue
                        )
                    },
                    readOnly = true
                )
                OwnerCard(
                    state,
                    onVisitProfileListener = {
                        viewModel.onEvent(
                            ViewMoreEstateEvents.OnVisitProfileClicked(
                                navHostController,
                                state.userId
                            )
                        )
                    },
                    onChattingListener = { receiverId: Int, receiverUsername: String,
                                           userImageUrl: String? ->
                        viewModel.onEvent(
                            ViewMoreEstateEvents.OnChattingClicked(
                                navHostController,
                                receiverId, receiverUsername, userImageUrl
                            )
                        )
                    },
                    onCallPhoneListener = {
                        viewModel.onEvent(
                            ViewMoreEstateEvents.OnCallPhoneClicked(
                                it,
                                context
                            )
                        )
                    }
                )
            }

            //RateScreen
            RatingScreen(
                isShowing = state.isShowingRateScreen,
                onResultListener = {
                    viewModel.onEvent(
                        ViewMoreEstateEvents.OnAddRateEstate(
                            estateId = estate_id,
                            rate = it,
                            context = context
                        )
                    )
                },
                onDismissRequest = {
                    viewModel.onEvent(ViewMoreEstateEvents.OnChangeShowRateScreen)
                })


        }
    }
}


@Composable
fun RatingBar(
    size: Dp,
    rating: Float,
    onRatingSelected: (Float) -> Unit = {}
) {
    Row(
        modifier = Modifier.padding(end = 5.dp)
    ) {
        for (i in 1..5) {
            val isSelected = i <= rating
            Log.d("qwe", isSelected.toString())
            StarIcon(
                isSelected = isSelected,
                onClick = { onRatingSelected(i.toFloat()) },
                size = size
            )
        }
    }
}

@Composable
private fun StarIcon(
    isSelected: Boolean,
    onClick: () -> Unit,
    size: Dp = 16.dp
) {
    val starColor = if (isSelected) darkYellow else Color.Gray

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(2.dp)
            .clip(CircleShape)
            .size(size)
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = starColor
        )
    }
}


@Composable
private fun MyTable(state: ViewMoreEstateStates) {
    Column(modifier = Modifier.background(color = veryLightBlue)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(lightblue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.estate_details),
                color = Color.White, fontWeight = FontWeight.Bold, fontSize = 25.sp
            )
            //end of estate details
        }
        BoxTable()
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    CellTable(R.string.estate_type_view_more, state.estateType)

                    //end of estate type
                    CellTable(R.string.operation_type_view_more, state.operationType)

                    //end of operation type
//                CellTable(R.string.governorate_view_more,state.governorate)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    CellTable(R.string.status_view_more, state.status)
                    //end of status
                    CellTable(R.string.space_view_more, state.space.toString() + " m2")
                    //end of space
                }
            }
            CellTable(R.string.address_view_more, state.address)

        }
    }


}

@Composable
private fun CellTable(nameOfCell: Int, state: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(veryLightBlue)
            .padding(top = 10.dp, bottom = 10.dp, start = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Text(
            text = stringResource(id = nameOfCell),
            fontSize = 12.sp, fontWeight = FontWeight.Bold
        )
        Text(
            text = state,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun BoxTable() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Black)
    )
}

@Composable
private fun OwnerCard(
    state: ViewMoreEstateStates,
    onVisitProfileListener: () -> Unit,
    onChattingListener: (receiverId: Int, receiverUsername: String, receiverImageUrl: String?) -> Unit,
    onCallPhoneListener: (phoneNumber: String) -> Unit
) {
    Text(
        text = stringResource(id = R.string.for_contacting),
        color = Color.Black,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textAlign = TextAlign.Start
    )


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
                    .background(color = lightblue),
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
                    if (state.userImageUrl != null) {
                        AsyncImage(
                            model = state.userImageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(10.dp)
                                .size(80.dp)
                                .border(
                                    border = BorderStroke(
                                        2.dp,
                                        Brush.sweepGradient(rainbowColors)
                                    ),
                                    shape = CircleShape
                                )
                                .clip(shape = CircleShape)
                        )
                    } else {
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
                    }
                    Text(
                        text = state.name,
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                    )
                }
                IconButton(onClick = {
                    onChattingListener(state.userId, state.name, state.userImageUrl)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Message,
                        contentDescription = null,
                        tint = darkYellow
                    )
                }

            }

            Row(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = null,
                        tint = lightblue,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = state.number,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                IconButton(onClick = {
                    onCallPhoneListener(state.number)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.phone_ic),
                        contentDescription = null,
                        tint = Color.Green
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
                    color = lightblue,
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



