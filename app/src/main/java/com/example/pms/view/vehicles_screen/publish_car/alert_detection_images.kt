package com.example.pms.view.vehicles_screen.publish_car

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
import com.example.pms.ui.theme.lightBlue

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
                text = stringResource(id = R.string.vehicle_caution_message) + " " + stringResource(
                    id = R.string.vehicle_caution_message_con
                )
            )
        },
        title = {
            Text(text = stringResource(id = R.string.vehicle_caution))
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(indicesList)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = lightBlue,
                    disabledBackgroundColor = lightBlue,
                    disabledContentColor = lightBlue
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