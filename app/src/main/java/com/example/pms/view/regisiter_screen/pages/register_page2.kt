package com.example.pms.view.regisiter_screen.pages


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor
import com.example.pms.view.regisiter_screen.InputPassword
import com.example.pms.view.regisiter_screen.InputTextFiled
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.ui.theme.transparent_p
import com.example.pms.view.animation.ProgressAnimatedBar
import com.example.pms.viewmodel.presentation_vm.register_vm.pages.page2.RegPage2Events
import com.example.pms.viewmodel.presentation_vm.register_vm.pages.page2.RegisterPage2Vm

@Composable
fun RegisterPage2(
    navController: NavHostController,
    viewModel: RegisterPage2Vm = viewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current

    InputTextFiled(
        title = stringResource(id = R.string.email),
        icon = R.drawable.email_ic,
        keyboardOption = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email
        ), onValueChanged = {
            viewModel.onEvent(RegPage2Events.EmailChanged(it))
        },
        isError = state.emailError != null
    )
    if (state.emailError != null) {
        Text(
            text = state.emailError ?: "",
            color = MaterialTheme.colors.error,
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }
    InputPassword(
        title = R.string.password,
        onValueChanged = {
            viewModel.onEvent(RegPage2Events.PasswordChanged(it))
        },
        isError = state.passwordError != null
    )
    if (state.passwordError != null) {
        Text(
            text = state.passwordError ?: "",
            color = MaterialTheme.colors.error,
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }
    InputPassword(
        title = R.string.confirm_password,
        onValueChanged = {
            viewModel.onEvent(RegPage2Events.RepeatedPasswordChanged(it))
        },
        isError = state.confirmPasswordError != null
    )

    if (state.confirmPasswordError != null) {
        Text(
            text = state.confirmPasswordError ?: "",
            color = MaterialTheme.colors.error,
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }
    if (!state.isLoading && !state.duplicatedEmail) {
        FloatingActionButton(
            onClick = {
                viewModel.onEvent(
                    RegPage2Events.OnSubmitClicked(
                        navController, context
                    )
                )
            },
            backgroundColor = iconsColor,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
    if (state.duplicatedEmail) {
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
                        RegPage2Events.OnDuplicatedEmailClicked(
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
                text = stringResource(id = R.string.need_verfication),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
    ProgressAnimatedBar(
        isLoading = state.isLoading,
        modifier = Modifier.size(46.dp)
    )
}