package com.example.pms.view.regisiter_screen.pages


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor
import com.example.pms.view.regisiter_screen.InputPassword
import com.example.pms.view.regisiter_screen.InputTextFiled
import com.example.pms.viewmodel.destinations.RegisterPages

@Composable
fun RegisterPage2(
    navController: NavHostController
) {

    InputTextFiled(title = stringResource(id = R.string.email),
        icon = R.drawable.email_ic,
        keyboardOption = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ), onValueChanged = {})

    InputPassword(
        title = R.string.password,
        onValueChanged = {

        })
    InputPassword(title = R.string.confirm_password,
        onValueChanged = {

        })

    FloatingActionButton(
        onClick = {
            RegisterPages.moveToNextRegisterPage(
                navController = navController,
                pageNumber = RegisterPages.registerPage3
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
