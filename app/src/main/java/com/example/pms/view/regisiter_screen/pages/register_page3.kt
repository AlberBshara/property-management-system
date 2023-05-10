package com.example.pms.view.regisiter_screen.pages


import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pms.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.pms.ui.theme.iconsColor
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.ui.theme.transparent_p
import com.example.pms.view.animation.ProgressAnimatedBar
import com.example.pms.view.utils.InternetAlertDialog
import com.example.pms.view.utils.RequestInternetPermission
import com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3.RegPage3Events
import com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3.RegisterPage3Vm

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RegisterPag3(
    navController: NavHostController,
    viewModel: RegisterPage3Vm = viewModel()
) {

    val state = viewModel.state

    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
        })

    imageUri?.let {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
        viewModel.onEvent(RegPage3Events.ImageChanged(imageUri!!))
    }


    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (bitmap == null) {
            Image(
                painter = painterResource(id = R.drawable.person_profile),
                contentDescription = "",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                bitmap = bitmap?.asImageBitmap()!!,
                contentDescription = "",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        FloatingActionButton(
            onClick = {
                launcher.launch("image/*")
            },
            backgroundColor = iconsColor,
            modifier = Modifier.align(Alignment.Bottom)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
    Text(
        text = stringResource(id = R.string.image_optional),
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
    )

    ProgressAnimatedBar(isLoading = state.isLoading)

    if (!state.isLoading) {
        Button(
            onClick = {
                viewModel.onEvent(RegPage3Events.Submit(navController, context))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp, top = 10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = iconsColor)
        ) {
            Text(
                text = stringResource(id = R.string.done),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
    if (state.emailDuplicated) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .wrapContentHeight()
                .background(transparent_p),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.onEvent(
                        RegPage3Events.DuplicatedEmailDone(
                            navController
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(iconsColor),
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
            Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = stringResource(id = R.string.email_duplicated),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }


    
    RequestInternetPermission(openRequestPermission = state.requestInternetPermission)
    InternetAlertDialog(
        onConfirm = {
            viewModel.onEvent(RegPage3Events.WifiCase.Confirm(context))
        }, onDeny = {
            viewModel.onEvent(RegPage3Events.WifiCase.Deny)
        },
        openDialog = state.showInternetAlert
    )
}





