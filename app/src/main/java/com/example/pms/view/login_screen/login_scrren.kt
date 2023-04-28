package com.example.pms.view.login_screen

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.example.pms.view.regisiter_screen.InputPassword
import com.example.pms.view.regisiter_screen.InputTextFiled
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.view.animation.RotateImage
import com.example.pms.ui.theme.background1
import com.example.pms.ui.theme.iconsColor
import com.example.pms.viewmodel.presentation_vm.login_vm.LoginScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.viewmodel.presentation_vm.login_vm.LoginEvents

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginScreenVM = viewModel()
) {

    viewModel.setContext(LocalContext.current)
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background1)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        RotateImage(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .size(150.dp)
        )

        Text(
            text = stringResource(id = R.string.app_name_without_abbreviation),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.welcome),
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        InputTextFiled(title = stringResource(id = R.string.email),
            icon = R.drawable.email_ic,
            keyboardOption = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ), onValueChanged = {
                viewModel.onEvent(LoginEvents.EmailChanged(it))
            }, isError = state.emailError
        )
        viewModel.emailState.value.error?.let {
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
        InputPassword(onValueChanged = {
            viewModel.onEvent(LoginEvents.PasswordChanged(it))
        }, isError = state.passwordError)
        viewModel.passwordState.value.error?.let {
            Text(
                text = it ,
                color =MaterialTheme.colors.error,
                fontSize = 10.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.forget_password),
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable {
                    viewModel.onEvent(LoginEvents.ForgotMyPassword(navController))
                },
            color = Color.White,
            fontSize = 12.sp
        )

        Button(
            onClick = {
                viewModel.onEvent(LoginEvents.Submit(navController))
            },
            modifier = Modifier
                .fillMaxWidth()

                .padding(start = 30.dp, end = 30.dp, top = 30.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = iconsColor)
        ) {
            Text(stringResource(id = R.string.login))
        }

        Text(
            text = stringResource(id = R.string.create_new_account),
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable {
                    viewModel.onEvent(LoginEvents.CreateNewAccount(navController))
                }
        )
    }
}
