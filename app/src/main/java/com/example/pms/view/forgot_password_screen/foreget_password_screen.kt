package com.example.pms.view.forgot_password_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.background1
import com.example.pms.ui.theme.iconsColor
import com.example.pms.ui.theme.transparent_p
import com.example.pms.view.regisiter_screen.InputTextFiled

@Composable
fun ForgetPassword(
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = background1),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(id = R.string.forget_password),
            modifier = Modifier.padding(top = 30.dp, end = 20.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Image(
            painter = painterResource(id = R.drawable.forget_password),
            contentDescription = "forgetPassword",
            modifier = Modifier.weight(0.3f)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
                .weight(0.3f),
            backgroundColor = transparent_p
        ) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = stringResource(id = R.string.enter_email),
                    modifier = Modifier.padding(10.dp),
                    fontWeight = FontWeight.Bold,
                )

                InputTextFiled(title = stringResource(id = R.string.email),
                    icon = R.drawable.email_ic,
                    keyboardOption = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ), onValueChanged = {
                    })


                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = iconsColor)
                ) {
                    Text(text = stringResource(id = R.string.reset_link))
                }
            }
        }
    }
}