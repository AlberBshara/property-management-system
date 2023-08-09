package com.example.pms.view.estates_screen.estate_home

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.noColor
import com.example.pms.view.animation.ProgressAnimatedBar
import com.example.pms.view.animation.shimmerEffect
import com.example.pms.view.estates_screen.estate_home.post_card.EstateCard
import com.example.pms.view.utils.RefreshScreen
import com.example.pms.view.utils.SearchBar
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeConstants
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeEvents
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeState
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun EstatesHomeScreen(
    navController: NavHostController,
    viewModel: EstateHomeVM = viewModel(),
) {

    val context = LocalContext.current
    viewModel.onEvent(EstateHomeEvents.OnStart(context = context))

    val state = viewModel.state

    val refreshingState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)





    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 53.dp)
    ) {
        AnimatedVisibility(
            visible = state.showSearchBar,
            enter = slideInHorizontally(),
        ) {
            SearchBar(
                query = state.query,
                onQueryListener = { viewModel.onEvent(EstateHomeEvents.SearchQueryChanged(it)) },
                onCancelListener = { viewModel.onEvent(EstateHomeEvents.CancelSearchBar(context = context)) },
                onDoneListener = {
                    viewModel.onEvent(
                        EstateHomeEvents.SearchServer(
                            text = state.query,
                            context = context
                        )
                    )

                    viewModel.onEvent(EstateHomeEvents.ShowSearchBar)
                }
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
                IconButton(onClick = {
                    viewModel.onEvent(EstateHomeEvents.ShowSearchBar)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        }
        //testing internet connection
        if (state.isLoading) {
            ShowShimmerEffect()
        } else {
            RefreshScreen(needRefresh = state.timeOut,
                onReloadListener = {
                    viewModel.onEvent(EstateHomeEvents.OnReloadClicked(context))
                })

            if (!state.timeOut) {


                SwipeRefresh(state = refreshingState,
                    onRefresh = {
                        viewModel.onEvent(EstateHomeEvents.OnRefresh(true, context))
                    }
                ) {

                    Column {
                        Row {
                            IconButton(
                                //modifier = Modifier.padding(3.dp),

                                onClick = {
                                    viewModel.onEvent(EstateHomeEvents.ShowDropDownFilterAll(open = true))
                                },
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.more_filter_ic),
                                        contentDescription = null,
                                        Modifier.size(30.dp),
                                        tint = Color.Black
                                    )
                                }

                            )

                            FilteringRowEstate(
                                titles = EstateHomeConstants.listOfFiltering,
                                onSelectedItem = {
                                    viewModel.onEvent(EstateHomeEvents.ShowDropDownFilter(it))
                                }
                            )


                        }


                        val scrollStateFilterAll = rememberLazyListState()

                        AnimatedVisibility(
                            visible = state.showDropDownFilterAll,
                            enter = slideInVertically()
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .scrollable(
                                        orientation = Orientation.Vertical,
                                        state = scrollStateFilterAll,
                                    ),


                                ) {
                                Column {
                                    ClearFiltersTopBar(
                                        clearFilters = { viewModel.onEvent(EstateHomeEvents.OnClearAllTextsFilter) },
                                        cancelFilter = {
                                            viewModel.onEvent(
                                                EstateHomeEvents.ShowDropDownFilterAll(
                                                    open = false
                                                )
                                            )
                                        }
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(color = Color.Gray)
                                    )



                                    FilteringElementsScreen(viewModel = viewModel, state = state)


                                }


                            }


                        }


                        val scrollState = rememberLazyListState()

                        AnimatedVisibility(
                            visible = state.showDropDownFilter,
                            enter = slideInVertically()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .scrollable(
                                        orientation = Orientation.Vertical,
                                        state = scrollState,
                                    ),


                                ) {


                                LazyColumn(
                                    state = scrollState,

                                    ) {

                                    if (state.filterId < 4) {
                                        items(EstateHomeConstants.listOfFiltering[state.filterId].items) { item ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                val textItem = stringResource(id = item)
                                                Text(
                                                    text = stringResource(id = item),
                                                    fontSize = 25.sp,
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .clickable {
                                                            if (state.filterId == 0) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "governorate",
                                                                        filterText = textItem,
                                                                        context = context
                                                                    )
                                                                )
                                                            }
                                                            if (state.filterId == 1) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "estate_type",
                                                                        filterText = textItem,
                                                                        context = context
                                                                    )
                                                                )
                                                            }
                                                            if (state.filterId == 2) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "operation_type",
                                                                        filterText = textItem,
                                                                        context = context
                                                                    )
                                                                )
                                                            }
                                                            if (state.filterId == 3) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "status",
                                                                        filterText = textItem,
                                                                        context = context
                                                                    )
                                                                )
                                                            }


                                                        }
                                                )

                                                RadioButton(selected = false, onClick = {
                                                })
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(1.dp)
                                                    .background(color = Color.LightGray)
                                            )

                                        }
                                    } else if (state.filterId == 4 || state.filterId == 5) {
                                        items(EstateHomeConstants.listOfPrice) { item ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = "$item $",
                                                    fontSize = 25.sp,
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .clickable {
                                                            if (state.filterId == 4) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "min_price",
                                                                        filterText = item.toString(),
                                                                        context = context
                                                                    )
                                                                )
                                                            }
                                                            if (state.filterId == 5) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "max_price",
                                                                        filterText = item.toString(),
                                                                        context = context
                                                                    )
                                                                )
                                                            }
                                                        }
                                                )

                                                RadioButton(selected = false, onClick = {

                                                })

                                            }

                                            Spacer(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(1.dp)
                                                    .background(color = Color.LightGray)
                                            )

                                        }

                                    } else if (state.filterId == 6 || state.filterId == 7) {

                                        items(EstateHomeConstants.listOfSpace) { item ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = "$item m2",
                                                    fontSize = 25.sp,
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .clickable {
                                                            if (state.filterId == 6) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "min_space",
                                                                        filterText = item.toString(),
                                                                        context = context
                                                                    )
                                                                )
                                                            }
                                                            if (state.filterId == 7) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "max_space",
                                                                        filterText = item.toString(),
                                                                        context = context
                                                                    )
                                                                )
                                                            }
                                                        }
                                                )

                                                RadioButton(selected = false, onClick = {

                                                })

                                            }

                                            Spacer(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(1.dp)
                                                    .background(color = Color.LightGray)
                                            )


                                        }
                                    } else {


                                        items(EstateHomeConstants.listOfLevel) { item ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = item.toString(),
                                                    fontSize = 25.sp,
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .clickable {
                                                            if (state.filterId == 8) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "min_level",
                                                                        filterText = item.toString(),
                                                                        context = context
                                                                    )
                                                                )
                                                            }
                                                            if (state.filterId == 9) {
                                                                viewModel.onEvent(
                                                                    EstateHomeEvents.FilterTypeChanged(
                                                                        filterType = "max_level",
                                                                        filterText = item.toString(),
                                                                        context = context
                                                                    )
                                                                )
                                                            }
                                                        }
                                                )

                                                RadioButton(selected = false, onClick = {


                                                })

                                            }

                                            Spacer(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(1.dp)
                                                    .background(color = Color.LightGray)
                                            )


                                        }


                                    }


                                }

                            }

                        }




                        ListContent(
                            state = state,
                            viewModel = viewModel,
                            context = context,
                            navController = navController
                        )


                    }

                }


            }


        }


    }


}

@Composable
private fun ListContent(
    state: EstateHomeState,
    viewModel: EstateHomeVM,
    context: Context,
    navController: NavHostController
) {


    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            // modifier = Modifier.weight(1f)
        ) {


            itemsIndexed(state.postsDataList) { index, item ->
                viewModel.estatesListScrollPosition = index
                if ((index + 1) >= (viewModel.pageNumber * EstateHomeVM.PAGE_SIZE)) {
                    viewModel.onEvent(EstateHomeEvents.Paginate(context))

                }

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

            item {

                if (state.gettingNewPosts) {
                    ProgressAnimatedBar(
                        isLoading = true,
                        modifier = Modifier.size(60.dp)
                    )
                }

            }
        }
    }

}

@Composable
private fun ClearFiltersTopBar(clearFilters: () -> Unit, cancelFilter: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = { clearFilters() },
                content = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            )
            Text(text = "Clear filters", fontSize = 14.sp)

        }

        IconButton(
            onClick = { cancelFilter() },
            content = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.padding(5.dp)
                )
            }
        )


    }


}


@Composable
fun ShowShimmerEffect() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 53.dp)
    ) {
        items(5) {
            PMSShimmerCard()
        }
    }
}

@Composable
fun PMSShimmerCard() {
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 3.dp)
                    .height(15.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(15.dp)
                    .padding(start = 10.dp, top = 3.dp, bottom = 10.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )
        }
    }
}
