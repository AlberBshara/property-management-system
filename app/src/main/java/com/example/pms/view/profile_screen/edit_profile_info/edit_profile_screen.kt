package com.example.pms.view.profile_screen.edit_profile_info


import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import  androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pms.R
import com.example.pms.ui.theme.*
import com.example.pms.view.profile_screen.ShimmerProfileLoading
import com.example.pms.view.utils.DialogLoading
import com.example.pms.view.utils.InternetAlertDialog
import com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm.EditProfileEvents
import com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm.EditProfileScreenVM

lateinit var urii: Uri

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun EditScreen(
    navController: NavHostController,
    viewModel: EditProfileScreenVM = viewModel()
) {

    val context = LocalContext.current
    viewModel.onEvent(EditProfileEvents.GetUserData(context))
    val state = viewModel.state

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                urii = uri
                viewModel.onEvent(EditProfileEvents.EditImageDialogOnChange)
            }

        }
    )

    if (state.isStarting) {
        ShimmerProfileLoading()
    } else
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .background(color = lightBlue),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    if (state.imageReceive == "") {
                        Image(
                            painter = painterResource(id = R.drawable.person_profile),
                            contentDescription = "",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        AsyncImage(
                            model = state.imageReceive,
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.edit_ic),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .clickable {
                                launcher.launch("image/*")
                            }
                            .padding(20.dp)
                            .size(35.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 2.dp),
                        ) {
                            EditProfileTextFields(
                                modifier = Modifier,
                                isError = false,
                                singleLine = false,
                                label = stringResource(id = R.string.first_name),
                                leadingIcon = R.drawable.profile_ic,
                                keyboardOptions = ImeAction.Next,
                                onValueChange = {
                                    viewModel.onEvent(EditProfileEvents.ChangeFirstName(it))
                                },
                                textState = state.firstName
                            )
                            state.firstNameError?.let {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp)
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 2.dp),
                        ) {
                            EditProfileTextFields(
                                modifier = Modifier,
                                isError = false,
                                singleLine = false,
                                label = stringResource(id = R.string.last_name),
                                leadingIcon = R.drawable.profile_ic,
                                keyboardOptions = ImeAction.Next,
                                onValueChange = {
                                    viewModel.onEvent(EditProfileEvents.ChangeLastName(it))
                                },
                                textState = state.lastName
                            )
                            state.lastNameError?.let {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp)
                                )
                            }
                        }
                    }

                    EditProfileTextFields(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        isError = false,
                        singleLine = false,
                        label = stringResource(id = R.string.phone),
                        leadingIcon = R.drawable.phone_ic,
                        keyboardOptions = ImeAction.Next,
                        onValueChange = {
                            viewModel.onEvent(EditProfileEvents.ChangePhoneNumber(it))
                        },
                        textState = state.phone
                    )

                    Text(
                        text = with(AnnotatedString.Builder()) {
                            append(stringResource(id = R.string.change_password))
                            addStyle(
                                style = SpanStyle(textDecoration = TextDecoration.Underline),
                                start = 0,
                                end = length
                            )
                            toAnnotatedString()
                        },
                        modifier = Modifier
                            .padding(bottom = 20.dp, top = 20.dp)
                            .clickable {
                                viewModel.onEvent(
                                    EditProfileEvents.ResetPasswordButton(
                                        navController
                                    )
                                )
                            },
                        fontSize = 12.sp
                    )

                    Button(
                        onClick = { viewModel.onEvent(EditProfileEvents.Submit(context)) },
                        modifier = Modifier.padding(20.dp),
                        colors = ButtonDefaults.buttonColors(lightBlue)
                    )
                    {
                        Text(
                            text = stringResource(id = R.string.done),
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }


        InternetAlertDialog(
            onConfirm = { viewModel.onEvent(EditProfileEvents.WifiCase.Confirm) },
            onDeny = { viewModel.onEvent(EditProfileEvents.WifiCase.Deny) },
            openDialog = state.showInternetAlert
        )

        if (state.editImageDialog) {
            AlertDialog(onDismissRequest = { /*TODO*/ },
                modifier = Modifier
                    .padding(20.dp)
                    .clip(shape = RoundedCornerShape(25.dp)),
                title = { Text(text = stringResource(id = R.string.attention), color = Color.Red) },
                text = { Text(text = stringResource(id = R.string.update_image)) },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.onEvent(EditProfileEvents.ChangeImage(urii, context))
                            viewModel.onEvent(EditProfileEvents.EditImageDialogOnChange)

                        }, modifier = Modifier.clip(shape = RoundedCornerShape(25.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = lightBlue,
                            disabledBackgroundColor = lightBlue
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            color = Color.White
                        )
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            viewModel.onEvent(EditProfileEvents.EditImageDialogOnChange)
                        }, modifier = Modifier.clip(shape = RoundedCornerShape(25.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = darkRed2,
                            disabledBackgroundColor = darkRed
                        )
                    ) {
                        Text(text = stringResource(id = R.string.cancel), color = Color.White)
                    }
                }
            )
        }
        DialogLoading(isLoading = state.isLoading)
    }
}
