package com.example.pms.view.forgot_password_screen


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor
import com.example.pms.ui.theme.lightBlue
import com.example.pms.view.animation.ProgressAnimatedBar
import com.example.pms.view.utils.InternetAlertDialog
import com.example.pms.viewmodel.presentation_vm.forget_password_vm.reset_password_vm.ResetPasswordScreenEvents
import com.example.pms.viewmodel.presentation_vm.forget_password_vm.reset_password_vm.ResetPasswordScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.view.utils.EditProfileTextFieldPassword
import com.example.pms.view.utils.TextFiledOutLined

@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ResetPasswordScreen(
    navController: NavHostController,
    viewModel: ResetPasswordScreenVM = viewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    viewModel.setContext(context)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProgressAnimatedBar(
            isLoading = state.progressBarIndicator,
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 20.dp)
        )
        Column {
            Text(
                text = (stringResource(id = R.string.put_code_reset_password)),
                modifier = Modifier.padding(start = 20.dp, bottom = 30.dp),
                fontWeight = FontWeight.Bold,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextFiledOutLined(
                    onValueChanged = {
                        viewModel.onEvent(ResetPasswordScreenEvents.OnCodeChange(it))
                    },
                    label = R.string.code,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 3.dp),
                    leadingIcon = R.drawable.lock_ic,
                    keyboardType = KeyboardType.Number,
                    enable = state.enableButtonSendCode
                )
                Button(
                    onClick = {
                        viewModel.onEvent(ResetPasswordScreenEvents.OnDoneCode(navController))
                    },
                    modifier = Modifier.padding(20.dp),
                    colors = ButtonDefaults.buttonColors(lightBlue),
                    enabled = state.enableButtonSendCode
                )
                {
                    Text(
                        text = stringResource(id = R.string.done),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = state.visiblePassword,
            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        ) {
            Column {
                EditProfileTextFieldPassword(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    isError = state.password_error,
                    singleLine = false,
                    label = stringResource(id = R.string.password),
                    leadingIcon = R.drawable.key_ic,
                    keyboardOptions = ImeAction.Next,
                    onValueChange = {
                        viewModel.onEvent(ResetPasswordScreenEvents.OnPasswordChange(it))
                    }
                )
                viewModel.passwordState.value.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp)
                    )
                }
                EditProfileTextFieldPassword(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    isError = false,
                    singleLine = false,
                    label = stringResource(id = R.string.confirm_password),
                    leadingIcon = R.drawable.key_ic,
                    keyboardOptions = ImeAction.Done,
                    onValueChange = {
                        viewModel.onEvent(ResetPasswordScreenEvents.OnConfirmPasswordChange(it))
                    }
                )
                state.confirm_password_error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp)
                    )
                }
                Button(
                    onClick = {
                        viewModel.onEvent(ResetPasswordScreenEvents.OnDonePassword(navController))
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally),
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

        if (state.showAlertMessageFromServer) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.onEvent(ResetPasswordScreenEvents.OnShowInternetAlertChange)
                },
                title = { Text(stringResource(id = R.string.error)) },
                text = { Text(state.errorMessage.toString()) },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.onEvent(ResetPasswordScreenEvents.OnShowAlertMessageFromServer)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = iconsColor)
                    ) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
                modifier = Modifier.padding(30.dp),
                backgroundColor = Color.White
            )
        }

        InternetAlertDialog(
            onConfirm = { viewModel.onEvent(ResetPasswordScreenEvents.WifiCase.Confirm) },
            onDeny = { viewModel.onEvent(ResetPasswordScreenEvents.WifiCase.Deny) },
            openDialog = state.showInternetAlert
        )
    }
}