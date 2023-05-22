package com.example.pms.view.vehicles_screen.publish_car


import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm.PublishVehicleVM
import  androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm.PublishVehicleEvents
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun PublishingCarScreen(
    navController: NavHostController,
    viewModel: PublishVehicleVM = viewModel()
) {


    val context = LocalContext.current

    val state = viewModel.state

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Expanded
    )
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val permission = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri> ->
            viewModel.onEvent(PublishVehicleEvents.AddNewImage(uris, context))
        })

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        modifier = Modifier
            .padding(bottom = 53.dp),
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            ExpandedBottomSheetContent(
                onAddPhotosListener = {
                    if (permission.status.isGranted) {
                        galleryLauncher.launch("image/*")
                    } else {
                        permission.launchPermissionRequest()
                    }
                },
                onLocationListener = { },
                onFileListener = { },
                onCameraListener = { }
            )
        },
    ) {
        //screen content:
        ScreenContent(
            navController = navController,
            viewModel = viewModel,
            state = state
        )

        if (state.imageDetectionCaution.isNotEmpty()) {
            AlertDialogForDetectedImage(
                indicesList = state.imageDetectionCaution,
                onConfirm = {
                   viewModel.onEvent(PublishVehicleEvents.ImageDetectionCautionOk(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    }
}



