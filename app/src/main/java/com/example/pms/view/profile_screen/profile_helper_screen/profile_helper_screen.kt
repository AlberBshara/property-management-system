package com.example.pms.view.profile_screen.profile_helper_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm.ProfileHelperScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.R
import com.example.pms.ui.theme.lightBlue
import com.example.pms.ui.theme.ligthGrey
import com.example.pms.view.animation.NoResultAnimation
import com.example.pms.view.animation.ShowShimmerEffect
import com.example.pms.view.utils.RefreshScreen
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm.ProfileHelperEvents


@Composable
fun ProfileHelperScreen(
    navController: NavHostController,
    viewModel: ProfileHelperScreenVM = viewModel(),
    from: String
) {

    val context = LocalContext.current
    viewModel.onEvent(ProfileHelperEvents.OnStart(from, context))
    val state = viewModel.state

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            IconButton(onClick = {
                viewModel.onEvent(ProfileHelperEvents.GoBackClicked(navController))
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                )
            }
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
                text = stringResource(
                    id = if (from == Destination.ProfileHelperScreen.FROM_FAV_CLICKED)
                        R.string.my_fav else R.string.my_posts
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.5f)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .clickable {
                    }
                    .background(
                        color = ligthGrey
                    ),
                contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.vehicles),
                    color = Color.Black,
                    fontWeight = FontWeight.W100,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.5f)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .clickable {
                    }
                    .background(
                        color = ligthGrey
                    ),
                contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.estates),
                    color = Color.Black,
                    fontWeight = FontWeight.W100,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        if (state.isLoading) {
            ShowShimmerEffect()
        } else {
            ListVehicleContent(
                state = state,
                viewModel = viewModel,
                context = context,
                navController = navController
            )
            RefreshScreen(needRefresh = state.needRefresh,
                onReloadListener = {
                    viewModel.onEvent(
                        ProfileHelperEvents.OnRefresh(
                            from, context
                        )
                    )
                })
            NoResultAnimation(
                isAnimating = state.noResult,
                modifier = Modifier
                    .size(350.dp)
            )
        }
    }
}

