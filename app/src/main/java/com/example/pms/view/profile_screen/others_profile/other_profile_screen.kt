package com.example.pms.view.profile_screen.others_profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pms.R
import com.example.pms.ui.theme.darkYellow
import com.example.pms.ui.theme.orange
import com.example.pms.view.animation.shimmerEffect
import com.example.pms.view.estates_screen.estate_home.PMSShimmerCard
import com.example.pms.view.estates_screen.estate_home.post_card.EstateCard
import com.example.pms.view.profile_screen.CardSocialMedia
import com.example.pms.view.utils.RefreshScreen
import com.example.pms.view.vehicles_screen.vehicle_home.NUMBER_OF_IMAGES
import com.example.pms.view.vehicles_screen.vehicle_home.post_card.PagerIndicator
import com.example.pms.viewmodel.presentation_vm.profile_vm.others_profile_vm.ProfileOtherEvents
import com.example.pms.viewmodel.presentation_vm.profile_vm.others_profile_vm.ProfileOtherState
import com.example.pms.viewmodel.presentation_vm.profile_vm.others_profile_vm.ProfileOtherVM
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState


@Composable
fun OtherProfileScreen(
    navController: NavHostController,
    viewModel: ProfileOtherVM = viewModel(),
    userId: Int
) {
    val context = LocalContext.current
    viewModel.onEvent(ProfileOtherEvents.OnStart(context = context, userId = userId))
    val state = viewModel.state

    if (state.isLoadingProfileData) {
        ShimmerProfileLoading()
    } else {
        RefreshScreen(needRefresh = state.needRefresh,
            onReloadListener = {
                viewModel.onEvent(ProfileOtherEvents.OnReloadClicked(context, userId))
            })
        if (!state.needRefresh) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ){
                TopBar(
                    name = state.name,
                    modifier = Modifier.padding(vertical = 20.dp),
                    onClickBack = {
                        viewModel.onEvent(
                            ProfileOtherEvents.GoBackClicked(
                                navController
                            )
                        )
                    },
                    onClickMessage = {
                        viewModel.onEvent(
                            ProfileOtherEvents.MessageClicked(
                                navController, userId
                            )
                        )
                    }
                )
                LazyColumn {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(4.dp))
                            ProfileSection(
                                numberOfPosts = state.numberOfPosts,
                                name = state.name,
                                email = state.email,
                                phone = state.phone,
                                state = state
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            SocialMediaBar(viewModel = viewModel)
                            EstateCarBar(viewModel = viewModel, context = context, userId = userId)
                        }
                    }
                    if (state.isLoadingEstateCar) {
                        items(3) {
                            PMSShimmerCard()
                        }
                    } else {
                        listContent(state = state,
                            navController = navController,
                            context = context,
                            likeClicked = {
                                viewModel.onEvent(
                                    ProfileOtherEvents.LikeClicked(
                                        type = "car",
                                        typeId = it,
                                        context
                                    )
                                )
                            },
                            viewMoreClicked = {
                                viewModel.onEvent(
                                    ProfileOtherEvents.OnViewMoreClicked(
                                        type = "car",
                                        typeId = it,
                                        navController = navController
                                    )
                                )
                            })
                    }
                }
            }

            HandleSocialMediaPress(
                state.faceBookPress,
                state.faceBookURL,
                ProfileOtherEvents.PressOnFacebook,
                context,
                viewModel
            )
            HandleSocialMediaPress(
                state.instagramPress,
                state.instagramURL,
                ProfileOtherEvents.PressOnInstagram,
                context,
                viewModel
            )
            HandleSocialMediaPress(
                state.twitterPress,
                state.twitterURL,
                ProfileOtherEvents.PressOnTwitter,
                context,
                viewModel
            )
        }
    }
}

@Composable
private fun HandleSocialMediaPress(
    pressState: Boolean,
    url: String,
    event: ProfileOtherEvents,
    context: Context,
    viewModel: ProfileOtherVM
) {
    if (pressState) {
        if (url == "") {
            Toast.makeText(context, "there is not any URL", Toast.LENGTH_SHORT).show()
            viewModel.onEvent(event)
        } else {
            WebPageScreen(url)
        }
    }
}


@Composable
private fun TopBar(
    name: String,
    modifier: Modifier,
    onClickBack: () -> Unit,
    onClickMessage: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickBack() },
        )
        Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Icon(
            imageVector = Icons.Filled.Message,
            contentDescription = "Message",
            tint = darkYellow,
            modifier = Modifier.clickable { onClickMessage() }
        )
    }
}

@Composable
private fun ProfileSection(
    numberOfPosts: String,
    name: String,
    email: String,
    phone: String,
    state: ProfileOtherState
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            if (state.imageUrl == null) {
                RounderImage(
                    image = painterResource(id = R.drawable.person_profile)
                )
            } else {
                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = Color.LightGray, shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            NumberPost(numberText = numberOfPosts, text = "Posts", modifier = Modifier.weight(7f))
        }
        Spacer(modifier = Modifier.height(4.dp))
        ProfileDescription(
            name = name,
            email = email,
            phone = phone
        )
    }
}

@Composable
private fun RounderImage(
    image: Painter
) {
    Image(
        painter = image,
        contentDescription = "",
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(width = 1.dp, color = Color.LightGray, shape = CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun NumberPost(
    numberText: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = numberText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}

@Composable
private fun ProfileDescription(
    name: String,
    email: String,
    phone: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            lineHeight = 20.sp
        )
        Text(
            text = email,
            letterSpacing = 0.5.sp,
            lineHeight = 20.sp
        )
        Text(
            text = phone,
            letterSpacing = 0.5.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun SocialMediaBar(viewModel: ProfileOtherVM) {
    Row(
        modifier = Modifier
            .padding(10.dp)
    ) {
        CardSocialMedia(
            facebookClickable = {
                viewModel.onEvent(ProfileOtherEvents.PressOnFacebook)
            },
            instagramClickable = {
                viewModel.onEvent(ProfileOtherEvents.PressOnInstagram)
            },
            twitterClickable = {
                viewModel.onEvent(ProfileOtherEvents.PressOnTwitter)
            }
        )
    }
}


@Composable
private fun EstateCarBar(viewModel: ProfileOtherVM, context: Context, userId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Button(
            onClick = {
                viewModel.onEvent(
                    ProfileOtherEvents.ChangeCarEstateList(
                        type = "car",
                        context = context,
                        userId = userId
                    )
                )
            },
            modifier = Modifier
                .weight(1f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            border = BorderStroke(1.dp, Color.Black),
            shape = RectangleShape
        ) {
            Text(text = "Car")
        }
        Spacer(modifier = Modifier.width(5.dp))
        Button(
            onClick = {
                viewModel.onEvent(
                    ProfileOtherEvents.ChangeCarEstateList(
                        type = "estate",
                        context = context,
                        userId = userId
                    )
                )
            },
            modifier = Modifier
                .weight(1f),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            border = BorderStroke(1.dp, Color.Black),
            shape = RectangleShape
        ) {
            Text(text = "Estate")
        }
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebPageScreen(urlToRender: String) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(urlToRender)
        }
    }, update = {
        it.loadUrl(urlToRender)
    })
}


@OptIn(ExperimentalPagerApi::class)
private fun LazyListScope.listContent(
    state: ProfileOtherState, navController: NavHostController,
    context: Context, likeClicked: (Int) -> Unit, viewMoreClicked: (Int) -> Unit
) {
    if (state.estateClicked) {
        itemsIndexed(state.estatesPostsList) { index, item ->
            EstateCard(
                navController = navController,
                onClick = { /*TODO*/ },
                lengthOfPagerIndicator = if (item.images.size < 3) item.images.size else 3,
                operationType = item.estateData.operation_type,
                price = item.estateData.price,
                location = item.estateData.governorate + "/" + item.estateData.address,
                bed = item.estateData.beds.toInt(),
                bath = item.estateData.beds.toInt(),
                garage = item.estateData.garage.toInt(),
                level = item.estateData.level.toInt(),
                idOfEstate = item.estateData.estate_id,
                images = item.images,
                loved = item.favourite,
                context = context
            )
        }
    }
    if (state.carClicked) {
        itemsIndexed(state.vehiclesPostsList) { index, item ->
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
                            text = "${item.vehicleData.price.toInt()} $",
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
                                likeClicked(item.vehicleData.id)
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
                                viewMoreClicked(item.vehicleData.id)
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


@Composable
private fun ShimmerProfileLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(130.dp)
                .shimmerEffect()
                .clip(shape = RoundedCornerShape(60.dp))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                .height(50.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(30.dp)
                    .padding(10.dp)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(30.dp)
                    .padding(10.dp)
                    .shimmerEffect()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(30.dp)
                    .padding(10.dp)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(30.dp)
                    .padding(10.dp)
                    .shimmerEffect()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(20.dp)
                .shimmerEffect()
        )
    }
}


