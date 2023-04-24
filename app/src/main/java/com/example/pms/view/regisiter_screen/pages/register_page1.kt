package com.example.pms.view.regisiter_screen.pages


import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor
import com.example.pms.view.regisiter_screen.InputTextFiled
import com.example.pms.view.regisiter_screen.InputPhoneWithCCP
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.viewmodel.presentation_vm.register_vm.pages.page1.RegisterPage1Vm

@Composable
fun RegisterPage1(
    navController: NavHostController,
    viewModel: RegisterPage1Vm = viewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current


    InputTextFiled(title = stringResource(id = R.string.first_name),
        icon = R.drawable.person_ic,
        keyboardOption = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        onValueChanged = {
            state.firstname = it
        }, isError = false
    )
    InputTextFiled(title = stringResource(id = R.string.last_name),
        icon = R.drawable.person_ic,
        keyboardOption = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        onValueChanged = {
            state.lastname = it
        }, isError = false
    )


    InputPhoneWithCCP(
        onValueChanged = { phone, code ->
            state.phoneNumber = code + phone
            Toast.makeText(
                context,
                "the obtained number $code-$phone", Toast.LENGTH_LONG
            ).show()
        }
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, end = 10.dp)
    ) {

        FloatingActionButton(
            onClick = {
                viewModel.page1Done(navController)
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

}
