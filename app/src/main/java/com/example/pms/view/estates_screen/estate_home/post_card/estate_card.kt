package com.example.pms.view.estates_screen.estate_home.post_card


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pms.R
import com.example.pms.model.EstateViewMoreData
import com.example.pms.ui.theme.darkRed
import com.example.pms.ui.theme.veryLightBlue
import com.example.pms.view.estates_screen.PagerIndicator
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeEvents
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeVM
import com.example.pms.viewmodel.presentation_vm.estates_vm.view_more_estate_vm.RoomsHomePost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun EstateCard(
    navController: NavHostController,
    onClick: () -> Unit,
    viewModel: EstateHomeVM = viewModel(),
    lengthOfPagerIndicator: Int,
    operationType: String,
    price: String,
    location: String,
    bed: Int,
    bath: Int,
    garage: Int,
    level: Int,
    idOfEstate: Int,
    images: List<EstateViewMoreData.ListOfImages>,
    loved: Boolean,
    context: Context
) {


    val pagerState = rememberPagerState(pageCount = lengthOfPagerIndicator, initialPage = 0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(),
            ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .padding(5.dp)
                    .onFocusChanged { }
            ) { current_image_index ->
                if (current_image_index < lengthOfPagerIndicator) {
                    AsyncImage(
                        model = images[current_image_index].imageUrl,
                        contentDescription = "Estate Image",
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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                PagerIndicator(current_index = pagerState.currentPage, lengthOfPagerIndicator)

            }


            Row(

                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {

                Row(

                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (operationType == "sell") {
                        Text(
                            text = "$price $",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 5.dp)
                        )

                    } else {
                        Text(
                            text = "$price $",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 5.dp)
                        )
                        Text(
                            text = " /month",
                            fontSize = 15.sp
                        )
                    }
                }
                Row {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(
                                EstateHomeEvents.LoveChanged(
                                    estateId = idOfEstate,
                                    context = context
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                    ) {
                        if (loved) {
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
                }


            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {

                Image(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(15.dp)
                )
                Text(
                    text = location,
                    fontSize = 10.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {

                RoomsHomePost(
                    bed = bed,
                    bath = bath,
                    garage = garage,
                    level = level
                ).listOfRooms.forEachIndexed { _, oneRoom ->

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


            Button(
                onClick = {
                    viewModel.onEvent(EstateHomeEvents.ViewMore(navController, idOfEstate))
                    onClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(veryLightBlue)
            ) {
                Text(
                    text = stringResource(id = R.string.view_more),
                    color = Color.Black,

                    )
            }


        }


    }
}