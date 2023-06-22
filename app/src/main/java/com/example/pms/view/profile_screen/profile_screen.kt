package com.example.pms.view.profile_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pms.ui.theme.lightBlue
import com.example.pms.view.animation.shimmerEffect
import com.example.pms.view.utils.RefreshScreen
import com.example.pms.viewmodel.presentation_vm.profile_vm.ProfileEvents
import com.example.pms.viewmodel.presentation_vm.profile_vm.ProfileScreenVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileScreenVM = viewModel()
) {

    val context = LocalContext.current
    viewModel.onEvent(ProfileEvents.OnStart(context))
    val state = viewModel.state

    val refreshingState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    if (state.isLoading) {
        ShimmerProfileLoading()
    } else {
        RefreshScreen(needRefresh = state.timeOut,
            onReloadListener = {
                viewModel.onEvent(ProfileEvents.OnReloadClicked(context))
            })
       if (!state.timeOut){
           SwipeRefresh(state = refreshingState,
               onRefresh = {
                   viewModel.onEvent(ProfileEvents.OnRefresh(true, context))
               }) {
               Column(
                   modifier = Modifier
                       .fillMaxSize()
                       .verticalScroll(rememberScrollState())
               ) {
                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .weight(0.7f)
                           .background(color = lightBlue)
                   ) {
                       Column(
                           modifier = Modifier
                               .fillMaxSize()
                               .padding(30.dp),
                           verticalArrangement = Arrangement.Center,
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           if (state.imageUrl == null) {
                               Image(
                                   painter = painterResource(id = R.drawable.person_profile),
                                   contentDescription = "",
                                   modifier = Modifier
                                       .size(100.dp)
                                       .clip(CircleShape),
                                   contentScale = ContentScale.Crop
                               )
                           } else {
                               AsyncImage(
                                   model = state.imageUrl,
                                   contentDescription = null,
                                   modifier = Modifier
                                       .size(100.dp)
                                       .clip(CircleShape),
                                   contentScale = ContentScale.Crop
                               )
                           }
                           Text(
                               text = state.name, fontSize = 22.sp, modifier = Modifier
                                   .weight(0.6f)
                                   .padding(20.dp), color = Color.White
                           )
                           Row(
                               modifier = Modifier
                                   .padding(start = 30.dp, end = 30.dp)
                                   .weight(0.3f)
                           ) {
                               CardSocialMedia(
                                   facebookClickable = {
                                       viewModel.onEvent(ProfileEvents.PressOnFacebook(navController))
                                   },
                                   instagramClickable = {
                                       viewModel.onEvent(ProfileEvents.PressOnInstagram(navController))
                                   },
                                   twitterClickable = {
                                       viewModel.onEvent(ProfileEvents.PressOnTwitter(navController))
                                   }
                               )
                           }
                       }

                       Row(
                           modifier = Modifier.fillMaxWidth(),
                           horizontalArrangement = Arrangement.End
                       ) {
                           IconButtonProfile(
                               icon = R.drawable.edit_ic,
                               onClick = {
                                   viewModel.onEvent(ProfileEvents.EditButton(navController))
                               },
                               color = Color.White
                           )
                       }
                   }

                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .weight(1f)
                           .background(color = Color.White)
                   ) {
                       Column(modifier = Modifier.fillMaxWidth()) {
                           CardEmailPhoneLocation(icon = R.drawable.email_ic, text = state.email)
                           CardEmailPhoneLocation(icon = R.drawable.phone_ic, text = state.phone)
                           CardEmailPhoneLocation(icon = R.drawable.location_icon, text = state.location)
                           CardsFavouritesPosts(
                               icon = R.drawable.love_icon2,
                               text = stringResource(id = R.string.my_fav),
                               onClick = { viewModel.onEvent(ProfileEvents.PressOnFavourites(navController)) })
                           CardsFavouritesPosts(
                               icon = R.drawable.posts_ic,
                               text = stringResource(id = R.string.my_posts),
                               onClick = { viewModel.onEvent(ProfileEvents.PressOnPosts(navController)) })
                       }
                   }
               }
           }
       }
    }
}


@Composable
private fun IconButtonProfile(
    icon: Int,
    onClick: () -> Unit,
    color: Color
) {
    IconButton(
        modifier = Modifier.padding(3.dp),
        onClick = {
            onClick()
        },
        content = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                Modifier.size(30.dp),
                tint = color
            )
        }
    )
}


@Composable
private fun CardEmailPhoneLocation(
    icon: Int,
    text: String
) {
    Row(
        modifier = Modifier
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = lightBlue,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(20.dp)
        )
        Text(text = text)
    }
}


@Composable
private fun CardsFavouritesPosts(
    icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RectangleShape)
            .padding(top = 20.dp, bottom = 20.dp, start = 30.dp, end = 30.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = lightBlue,
            disabledBackgroundColor = lightBlue
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(0.2f),
            )
            Text(text = text, fontSize = 20.sp, modifier = Modifier.weight(1f))
        }
    }
}


@Composable
 fun ShimmerProfileLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
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