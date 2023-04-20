package com.example.pms.view.regisiter_screen.pages


import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor
import com.example.pms.view.regisiter_screen.InputTextFiled
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.view.regisiter_screen.InputPhoneWithCCP


@Composable
fun RegisterPage1(
    navController: NavHostController
) {

    val context = LocalContext.current

    InputTextFiled(title = stringResource(id = R.string.first_name),
        icon = R.drawable.person_ic,
        keyboardOption = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        onValueChanged = {
        })
    InputTextFiled(title = stringResource(id = R.string.last_name),
        icon = R.drawable.person_ic,
        keyboardOption = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        onValueChanged = {})


    InputPhoneWithCCP(
        onValueChanged = { phone, code ->
            Toast.makeText(
                context,
                "the obtained number $code-$phone", Toast.LENGTH_LONG
            ).show()
        }
    )
    FloatingActionButton(
        onClick = {
            RegisterPages.moveToNextRegisterPage(
                navController = navController,
                pageNumber = RegisterPages.registerPage2
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


