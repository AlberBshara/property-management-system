package com.example.pms.view.regisiter_screen.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor
import com.example.pms.view.animation.ProgressAnimatedBar
import com.example.pms.view.utils.TextFiledOutLined
import com.example.pms.viewmodel.presentation_vm.register_vm.pages.page4.RegisterPage4Vm
import com.example.pms.viewmodel.presentation_vm.register_vm.pages.page4.RegisterPage4events

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RegisterPag4(
    navController: NavHostController,
    viewModel: RegisterPage4Vm = androidx.lifecycle.viewmodel.compose.viewModel()
    ){

    val state=viewModel.state
    val context= LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = stringResource(id = R.string.verifyEmail),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 5.dp)
        )

        Text(
            text = stringResource(id = R.string.putTheCode),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier=Modifier.padding(top = 100.dp, start = 10.dp, end = 10.dp)
        )

        TextFiledOutLined(
            onValueChanged = {
                viewModel.onEvent(RegisterPage4events.OnCodeChange(code = it))
            },
            label = R.string.code,
            modifier = Modifier
                .padding(20.dp),
            leadingIcon = R.drawable.lock_ic,
            keyboardType = KeyboardType.Number)
        ProgressAnimatedBar(isLoading = state.isLoading,
            modifier = Modifier.size(60.dp))

        if(!state.isLoading) {
            Button(
                onClick = {
                    viewModel.onEvent(RegisterPage4events.OnDone(
                         state.code, context, navController)
                    )
                },
                modifier = Modifier.padding(20.dp),
                colors = ButtonDefaults.buttonColors(iconsColor),
            )
            {
                Text(
                    text = stringResource(id = R.string.done),
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}

