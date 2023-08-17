package com.example.pms.view.regisiter_screen.pages


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
import com.example.pms.view.regisiter_screen.InputTextFiled
import com.example.pms.view.regisiter_screen.InputPhoneWithCCP
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.view.animation.ProgressAnimatedBar
import com.example.pms.viewmodel.presentation_vm.register_vm.pages.page1.RegPage1Events
import com.example.pms.viewmodel.presentation_vm.register_vm.pages.page1.RegisterPage1Vm

@Composable
fun RegisterPage1(
    navController: NavHostController,
    viewModel: RegisterPage1Vm = viewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current


    InputTextFiled(
        title = stringResource(id = R.string.first_name),
        icon = R.drawable.person_ic,
        keyboardOption = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        onValueChanged = {
            viewModel.onEvent(RegPage1Events.FirstNameChanged(it))
        }, isError = state.firstname_error != null
    )
    if (state.firstname_error != null) {
        Text(
            text = state.firstname_error ?: "",
            color = MaterialTheme.colors.error,
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }
    InputTextFiled(
        title = stringResource(id = R.string.last_name),
        icon = R.drawable.person_ic,
        keyboardOption = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        onValueChanged = {
            viewModel.onEvent(RegPage1Events.LastNameChanged(it))
        }, isError = state.lastname_error != null
    )
    if (state.lastname_error != null) {
        Text(
            text = state.lastname_error ?: "",
            color = MaterialTheme.colors.error,
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }

    InputPhoneWithCCP(
        onValueChanged = { phone, code ->
            viewModel.onEvent(RegPage1Events.PhoneNumberChanged(phone, code))
        }
    )
    if (state.phoneNumber_error != null) {
        Text(
            text = state.phoneNumber_error ?: "",
            color = MaterialTheme.colors.error,
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }
    if (state.countryCode_error != null) {
        Text(
            text = state.countryCode_error ?: "",
            color = MaterialTheme.colors.error,
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, end = 10.dp)
    ) {
        if(!state.isLoading) {
            FloatingActionButton(
                onClick = {
                    viewModel.submitPage1(navController, context)
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
        ProgressAnimatedBar(
            isLoading = state.isLoading,
            modifier = Modifier.size(46.dp)
        )
    }
}
