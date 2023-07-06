package com.example.pms.view.vehicles_screen.vehicle_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.pms.view.utils.RatingBar
import com.example.pms.R
import com.example.pms.ui.theme.lightBlue
import com.example.pms.view.animation.RatingAnimation

@Composable
fun RatingScreen(
    isShowing: Boolean,
    onResultListener: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (isShowing) {
        val rating = remember {
            mutableStateOf(0)
        }
        Dialog(onDismissRequest = {
            onDismissRequest()
        }) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(color = Color.White)
                    .clip(shape = RoundedCornerShape(14.dp))
            ) {
                RatingAnimation(
                    isAnimating = true,
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))

                RatingBar(size = 24.dp, rating = rating.value,
                    onRatingSelected = {
                        rating.value = it
                    })
                Button(
                    onClick = {
                        onResultListener(rating.value)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = lightBlue,
                        disabledBackgroundColor = lightBlue
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        color = Color.White,
                        style = MaterialTheme.typography.button,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}