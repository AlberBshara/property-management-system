package com.example.pms.view.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.lightGreen

@Composable
fun SuccessDialog(
    showIt: Boolean,
    onOkButtonListener: () -> Unit
) {
    if (showIt) {
        AlertDialog(onDismissRequest = {},
            title = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                ) {
                    Icon(
                        imageVector = Icons.Filled.DoneOutline,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(60.dp)
                    )
                }
            },
            text = {
                Text(
                    text = stringResource(id = R.string.submitted),
                    color = Color.Black,
                    style = MaterialTheme.typography.subtitle1
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onOkButtonListener()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = lightGreen,
                        disabledBackgroundColor = lightGreen
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        color = Color.White,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            })
    }
}