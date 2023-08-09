package com.example.pms.view.estates_screen.publish_estate

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.lightGreen

@Composable
fun AlertDialogForDetectedImage(
    indicesList: List<Int>,
    onConfirm: (List<Int>) -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {

        },
        text = {
            Text(
                stringResource(id = R.string.estate_caution_message1) + " " + stringResource(
                    id = R.string.estate_caution_message2
                )
            )
        },
        title = {
            Text(text = stringResource(id = R.string.estate_caution))
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(indicesList)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = lightGreen,
                    disabledBackgroundColor = lightGreen,
                    disabledContentColor = lightGreen
                )
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    )

}