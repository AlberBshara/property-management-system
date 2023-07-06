package com.example.pms.view.chatting_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import com.example.pms.viewmodel.presentation_vm.chatting_vm.ChattingScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.R
import com.example.pms.ui.theme.noColor
import com.example.pms.view.animation.shimmerEffect
import com.example.pms.view.chatting_screen.messages_screen.NoMessagesYetScreen
import com.example.pms.view.utils.RefreshScreen
import com.example.pms.view.vehicles_screen.vehicle_home.SearchBar
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.chatting_vm.ChattingEvents

@Composable
fun ChattingMainScreen(
    navController: NavHostController,
    viewModel: ChattingScreenVM = viewModel()
) {

    val context = LocalContext.current
    viewModel.onEvent(ChattingEvents.OnStart(context))
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 53.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = state.showSearchBar,
            enter = slideInHorizontally(),
        ) {
            SearchBar(
                query = state.query,
                onCancelListener = {
                    viewModel.onEvent(ChattingEvents.OnSearchStateChanged)
                },
                onQueryListener = {},
                onDone = {}
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .clickable {
                                viewModel.onEvent(ChattingEvents.OnSearchStateChanged)
                            }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.more_ic),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .clickable {
                            }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
        if (state.isLoading) {
            ShimmerChatLoading()
        } else {
            RefreshScreen(needRefresh = state.needRefresh,
                onReloadListener = {
                    viewModel.onEvent(ChattingEvents.OnRefreshClicked(
                        context
                    ))
                })
            NoMessagesYetScreen(isAnimating = state.noChattingYet)
            LazyColumn {
                items(state.chattingItemList) {
                    ChattingItemCard(
                        onClinkListener = {
                            navController.navigate(Destination.MessagesDestination.route)
                        },
                        chattingItemData = it,
                        onCallingPhoneListener = {
                        }
                    )
                }
            }
        }
    }
}


@Composable
private fun ShimmerChatLoading() {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        for (i in 0..4) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .shimmerEffect()
                        .size(50.dp)
                        .padding(10.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(15.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}



























