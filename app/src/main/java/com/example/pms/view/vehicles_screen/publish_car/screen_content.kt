package com.example.pms.view.vehicles_screen.publish_car

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.view.vehicles_screen.vehicle_home.post_card.PagerIndicator
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm.PublishVehicleEvents
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm.PublishVehicleState
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm.PublishVehicleVM
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ScreenContent(
    navController: NavHostController,
    viewModel: PublishVehicleVM,
    state: PublishVehicleState
) {

    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val pagerState = rememberPagerState(pageCount = state.enteredData.listOfSelectedImages.size)

    val permission = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri> ->
            viewModel.onEvent(PublishVehicleEvents.AddNewImage(uris, context))
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
        ) {
            item {
                Column(
                    modifier = Modifier
                        .height(218.dp)
                        .fillMaxWidth()
                        .graphicsLayer {
                            viewModel.onEvent(
                                PublishVehicleEvents.ImageCollapsing(
                                    lazyListState, this
                                )
                            )
                        },
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.enteredData.listOfSelectedImages.isEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.add_img),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(
                                    shape = RoundedCornerShape(
                                        bottomEnd = 16.dp,
                                        bottomStart = 16.dp
                                    )
                                )
                                .clickable {
                                   if (permission.status.isGranted){
                                       galleryLauncher.launch("image/*")
                                   }else{
                                       permission.launchPermissionRequest()
                                   }
                                },
                            contentScale = ContentScale.Inside
                        )
                        PagerIndicator(current_index = state.indexOfCurrentImage, length = 1)
                    } else {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        ) {
                            viewModel.onEvent(PublishVehicleEvents.ImageIndexChanged(it))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                AsyncImage(
                                    model = state.enteredData.listOfSelectedImages[it],
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
                                IconButton(onClick = {
                                    viewModel.onEvent(PublishVehicleEvents.OnRemoveImage(it))
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                        PagerIndicator(
                            current_index = state.indexOfCurrentImage,
                            length = state.enteredData.listOfSelectedImages.size
                        )
                    }
                }
            }
            item {
                PublishingVehicleForm(
                    navController = navController,
                    viewModel = viewModel,
                    state = state
                )
            }
        }
    }
}
