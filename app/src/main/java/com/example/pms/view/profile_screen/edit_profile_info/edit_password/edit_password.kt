package com.example.pms.view.profile_screen.edit_profile_info.edit_password


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor
import com.example.pms.view.utils.DialogLoading
import com.example.pms.view.utils.InternetAlertDialog
import com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm.edit_password_vm.EditPasswordScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.ui.theme.lightBlue
import com.example.pms.view.profile_screen.edit_profile_info.EditProfileTextFieldPassword
import com.example.pms.view.utils.AlertDialogOk
import com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm.edit_password_vm.EditPasswordEvents

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun EditPasswordScreen(
    navHostController: NavHostController,
    viewModel: EditPasswordScreenVM = viewModel()
) {
    val state = viewModel.state
    viewModel.setContext(LocalContext.current)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

        Text(
            text = stringResource(id = R.string.reset_password),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = lightBlue
        )

        Spacer(modifier = Modifier.padding(20.dp))

        EditProfileTextFieldPassword(
            modifier = Modifier
                .fillMaxWidth(),
            isError = false,
            singleLine = false,
            label = stringResource(id = R.string.old_password),
            leadingIcon = R.drawable.lock_ic,
            keyboardOptions = ImeAction.Next,
            onValueChange = {
                viewModel.onEvent(EditPasswordEvents.ChangeOldPassword(it))
            },
            textState = state.oldPassword
        )

        Spacer(modifier = Modifier.padding(10.dp))

        EditProfileTextFieldPassword(
            modifier = Modifier
                .fillMaxWidth(),
            isError = state.passwordError,
            singleLine = false,
            label = stringResource(id = R.string.password),
            leadingIcon = R.drawable.password_vector,
            keyboardOptions = ImeAction.Next,
            onValueChange = {
                viewModel.onEvent(EditPasswordEvents.ChangePassword(it))
            },
            textState = state.password
        )
        viewModel.passwordState.value.error?.let {
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
        Spacer(modifier = Modifier.padding(10.dp))

        EditProfileTextFieldPassword(
            modifier = Modifier
                .fillMaxWidth(),
            isError = false,
            singleLine = false,
            label = stringResource(id = R.string.confirm_password),
            leadingIcon = R.drawable.password_vector,
            keyboardOptions = ImeAction.Done,
            onValueChange = {
                viewModel.onEvent(EditPasswordEvents.ChangeConfirmPassword(it))
            },
            textState = state.confirmPassword
        )
        state.confirmPasswordError?.let {
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

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                viewModel.onEvent(EditPasswordEvents.Submit)
            },
            modifier = Modifier.padding(20.dp),
            colors = ButtonDefaults.buttonColors(lightBlue)
        )
        {
            Text(text = stringResource(id = R.string.done), color = Color.White, fontSize = 18.sp)
        }
    }

    InternetAlertDialog(
        onConfirm = { viewModel.onEvent(EditPasswordEvents.WifiCase.Confirm) },
        onDeny = { viewModel.onEvent(EditPasswordEvents.WifiCase.Deny) },
        openDialog = state.showInternetAlert
    )

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(EditPasswordEvents.ShowDialog)
            },
            title = { Text(stringResource(id = R.string.error)) },
            text = { Text(state.errorMessage.toString()) },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onEvent(EditPasswordEvents.ShowDialog)
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
    if (state.successEditing) {
        AlertDialogOk(
            title = stringResource(id = R.string.done),
            message = stringResource(id = R.string.updtaded_password),
            onDismissRequest = {
                viewModel.onEvent(
                    EditPasswordEvents.SuccessEditingPasswordChanged(
                        false
                    )
                )
            },
            onClickButton = {
                viewModel.onEvent(
                    EditPasswordEvents.SuccessEditingPasswordChanged(
                        false
                    )
                )
            }
        )
    }
    DialogLoading(isLoading = state.isLoading)
}

