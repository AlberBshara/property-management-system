package com.example.pms.view.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.example.pms.R
import com.example.pms.ui.theme.lightBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PricePicker(
    isPicking: Boolean,
    onPickedListener: (minimum: Double, maximum: Double) -> Unit
) {
    val range = remember {
        mutableStateOf(0f..100000000f)
    }

    if (isPicking) {
        Dialog(onDismissRequest = { true }) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color.White)
                    .wrapContentHeight()
            ) {
                Text(
                    text = stringResource(id = R.string.pick_price),
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                )

                Text(
                    text = "between ${range.value.start.toInt()} -> ${range.value.endInclusive.toInt()}",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                RangeSlider(
                    values = range.value,
                    onValueChange = {
                        range.value = it
                    },
                    valueRange = 0f..10000000000f,
                    steps = 1000,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = lightBlue,
                        activeTrackColor = lightBlue
                    )
                )

                Button(
                    onClick = {
                        onPickedListener(
                            range.value.start.toDouble(),
                            range.value.endInclusive.toDouble()
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = lightBlue,
                        disabledBackgroundColor = lightBlue
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        style = MaterialTheme.typography.button,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }

            }
        }
    }
}