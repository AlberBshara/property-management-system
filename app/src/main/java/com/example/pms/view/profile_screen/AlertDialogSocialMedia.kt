package com.example.pms.view.profile_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pms.R
import com.example.pms.ui.theme.lightblue
import com.example.pms.ui.theme.transparentGray
import com.example.pms.view.utils.DialogLoading


@Composable
fun AlertDialogSocialMedia(
    onValueChanged: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onDoneRequest: () -> Unit,
    loading: Boolean,
    url: String
    ) {

    val state = remember {
        mutableStateOf(url)
    }

    AlertDialog(
        modifier = Modifier
            .padding(20.dp)
            .clip(shape = RoundedCornerShape(12)),
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = stringResource(id = R.string.copy_paste))},
        text = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp),
                value = state.value,
                onValueChange = {
                    state.value = it
                    onValueChanged(it)
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = transparentGray,
                    focusedBorderColor = lightblue,
                    unfocusedBorderColor = transparentGray,
                    cursorColor = lightblue
                ),
                shape = RoundedCornerShape(0.dp),
                label = {
                    Text(
                        text = "URL",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.caption
                    )
                },
                singleLine = true,


                )


            DialogLoading(isLoading = loading)


        },

        confirmButton = {

            Button(
                onClick = { onDoneRequest() },
                modifier = Modifier.padding(15.dp),
                colors = ButtonDefaults.buttonColors(lightblue)
            )
            {
                Text(
                    text = stringResource(id = R.string.done),
                    color = Color.White,
                    fontSize = 18.sp
                )

            }

        },

        )


}