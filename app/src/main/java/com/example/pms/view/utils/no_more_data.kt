package com.example.pms.view.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.lightBlue

@Composable
fun NoMoreDataAvailable(
    showIt: Boolean,
    onResetListener: () -> Unit
) {
    if (showIt) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.no_more_data),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(bottom = 10.dp, top = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Button(onClick = {
                onResetListener()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = lightBlue,
                disabledBackgroundColor = lightBlue
            )) {
                Text(
                    text = stringResource(id = R.string.reset),
                    color = Color.White,
                    style = MaterialTheme.typography.button
                )
            }

        }
    }
}