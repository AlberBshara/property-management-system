package com.example.pms.view.estates_screen.publish_estate


import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pms.R
import com.example.pms.ui.theme.lightblue
import com.example.pms.view.estates_screen.PagerIndicator
import com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm.PublishEstateEvents
import com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm.PublishEstateState
import com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm.PublishEstateVM
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class, ExperimentalPermissionsApi::class)
@Composable
fun ScreenContent(
    viewModel: PublishEstateVM,
    state: PublishEstateState
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
            viewModel.onEvent(PublishEstateEvents.AddNewImage(uris, context))
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
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.enteredData.listOfSelectedImages.isEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_photo_ic),
                            tint = lightblue,
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
                                    if (permission.status.isGranted) {
                                        galleryLauncher.launch("image/*")
                                    } else {
                                        permission.launchPermissionRequest()
                                    }
                                },

                            )

                        PagerIndicator(current_index = state.indexOfCurrentImage, length = 1)
                    } else {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            viewModel.onEvent(PublishEstateEvents.OnImageIndexChanged(pagerState.currentPage))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                AsyncImage(
                                    model = state.enteredData.listOfSelectedImages[pagerState.currentPage],
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),

                                    contentScale = ContentScale.FillWidth
                                )
                                IconButton(onClick = {
                                    viewModel.onEvent(PublishEstateEvents.OnRemoveImage(pagerState.currentPage))
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                            }
                        }

                    }

                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PagerIndicator(
                        current_index = pagerState.currentPage,
                        length = state.enteredData.listOfSelectedImages.size
                    )
                }

            }

            item {
                PublishingEstateForm(
                    viewModel = viewModel,
                    state = state
                )
            }


        }
    }
}
