package com.example.pms.view.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pms.ui.theme.transparentGray
import com.example.pms.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.pms.ui.theme.lightBlue


private const val year: Int = 1970

@Composable
fun YearDialogPicker(
    showDialog: Boolean,
    selectedYear: (String) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    OnLeadingIcon : () -> Unit
) {

    var value by remember {
        mutableStateOf("")
    }

        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = {},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = transparentGray,
                focusedBorderColor = lightBlue,
                unfocusedBorderColor = transparentGray,
                cursorColor = lightBlue
            ),
            shape = RoundedCornerShape(20.dp),
            label = {
                Text(
                    text = stringResource(id = R.string.manufacture_year),
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.caption
                )
            },
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    OnLeadingIcon()
                }) {
                    Icon(imageVector = Icons.Filled.Add,
                        contentDescription = null)
                }
            }
        )


    AnimatedVisibility(
        visible = showDialog,
        enter = fadeIn()
    ) {
        AlertDialog(
            onDismissRequest = {
                onDismissRequest()
            },
            title = { Text(text = stringResource(id = R.string.chose_year)) },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LazyRow {
                        items(55) {
                            val currentYear = year + it
                            Text(
                                text = "$currentYear",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        selectedYear(currentYear.toString())
                                        value = currentYear.toString()
                                    },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.forward_ic),
                        contentDescription = null,
                        modifier = Modifier.padding(10.dp),
                    )
                }
            },
            confirmButton = {})
    }


}