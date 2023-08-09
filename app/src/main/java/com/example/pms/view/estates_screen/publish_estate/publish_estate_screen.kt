package com.example.pms.view.estates_screen.publish_estate

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.ui.theme.doublelightblue
import com.example.pms.view.utils.DialogLoading
import com.example.pms.view.utils.InternetAlertDialog
import com.example.pms.view.utils.SuccessDialog
import com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm.PublishEstateEvents
import com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm.PublishEstateVM
import com.example.pms.viewmodel.utils.ImageHelper
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun PublishingEstateScreen(
    viewModel: PublishEstateVM = viewModel()
) {


    val state = viewModel.state

    val context = LocalContext.current

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
        //Expanded
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
            viewModel.onEvent(PublishEstateEvents.AddNewImage(uris, context))
        })


    val permission2 = Manifest.permission.CAMERA
    val PERMISSION_REQUEST_CAMERA = 1
    val myImage: Bitmap =
        BitmapFactory.decodeResource(Resources.getSystem(), android.R.mipmap.sym_def_app_icon)
    val result = remember { mutableStateOf(myImage) }
    val loadImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                result.value = bitmap

                val cameraUri = ImageHelper.saveBitmapAsPng(context, bitmap, "CameraJony")
                viewModel.onEvent(PublishEstateEvents.AddNewImage(listOf(cameraUri!!), context))
            }
        })





    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        modifier = Modifier
            .padding(bottom = 53.dp),
        sheetBackgroundColor = doublelightblue,
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
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
                onCameraListener = {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            permission2
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(permission2),
                            PERMISSION_REQUEST_CAMERA
                        )
                    } else {
                        loadImage.launch(null)
                    }


                }

            )

        }


    ) {

        ScreenContent(
            viewModel = viewModel,
            state = state

        )


        if (state.fakeImagesIndexes.isNotEmpty()) {
            AlertDialogForDetectedImage(
                indicesList = state.fakeImagesIndexes,
                onConfirm = {
                    viewModel.onEvent(PublishEstateEvents.ImageDetectionCautionOk(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

    }


    InternetAlertDialog(
        onConfirm = { viewModel.onEvent(PublishEstateEvents.WifiCase.Confirm) },
        onDeny = { viewModel.onEvent(PublishEstateEvents.WifiCase.Deny) },
        openDialog = state.showInternetAlert
    )


    DialogLoading(isLoading = state.isLoading)


    SuccessDialog(showIt = state.successSendData,
        onOkButtonListener = {
            viewModel.onEvent(PublishEstateEvents.OnDoneSuccessSendDataClicked)
        })


}