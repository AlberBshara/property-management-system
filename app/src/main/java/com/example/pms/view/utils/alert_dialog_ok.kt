package com.example.pms.view.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor

@Composable
fun AlertDialogOk(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    onClickButton: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            Button(
                onClick = {
                    onClickButton()
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