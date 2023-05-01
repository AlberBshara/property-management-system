package com.example.pms.view.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pms.R

@Composable
fun InternetAlertDialog(
    onConfirm: () -> Unit,
    onDeny: () -> Unit,
    openDialog: Boolean
) {
    if (openDialog) {
        AlertDialog(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            onDismissRequest = {},
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.internet),
                        contentDescription = "",
                        modifier = Modifier.size(125.dp)
                    )
                }
            },
            text = {
                Text(text = stringResource(id = R.string.internet_unconnected))
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDeny()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = stringResource(id = R.string.deny))
                }
            })
    }
}

