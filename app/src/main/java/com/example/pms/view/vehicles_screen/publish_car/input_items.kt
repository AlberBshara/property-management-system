package com.example.pms.view.vehicles_screen.publish_car

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.lightGreen
import com.example.pms.ui.theme.transparentGray
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType


private const val DEFAULT_LEADING_ICON: Int = -1

@Composable
fun PMSOutLinedTextField(
    modifier: Modifier = Modifier,
    initialValue: String,
    onValueChanged: (String) -> Unit,
    label: Int,
    singleLine: Boolean = true,
    keyboardType: KeyboardType,
    leadingIcon: Int = DEFAULT_LEADING_ICON
) {

    var value by remember {
        mutableStateOf(initialValue)
    }

    OutlinedTextField(
        value = value,
        onValueChange = {
            value = it
            onValueChanged(it)
        },
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = transparentGray,
            focusedBorderColor = lightGreen,
            unfocusedBorderColor = transparentGray,
            cursorColor = lightGreen
        ),
        shape = RoundedCornerShape(20.dp),
        label = {
            Text(
                text = stringResource(id = label),
                color = Color.DarkGray,
                style = MaterialTheme.typography.caption
            )
        },
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        leadingIcon = {
            if (leadingIcon != DEFAULT_LEADING_ICON) {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null
                )
            }
        }
    )
}


@Composable
fun PMSDropDownOutLinedTextFiled(
    initialValue: String,
    onValueChanged: (String) -> Unit,
    label: Int,
    listMenuItems: List<String>,
    modifier: Modifier = Modifier
) {


    var isMenuVisible by remember {
        mutableStateOf(false)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var readOnly by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                value = it
                onValueChanged(it)
            },
            trailingIcon = {
                IconButton(onClick = {
                    // open the Menu :
                    isMenuVisible = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.drop_down_ic),
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = transparentGray,
                focusedBorderColor = lightGreen,
                unfocusedBorderColor = transparentGray,
                cursorColor = lightGreen
            ),
            shape = RoundedCornerShape(20.dp),
            label = {
                Text(
                    text = stringResource(id = label),
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.caption
                )
            },
            singleLine = true,
            readOnly = readOnly,

            )
        DropdownMenu(expanded = isMenuVisible,
            onDismissRequest = { isMenuVisible = false }
        ) {
            Column(
                modifier = Modifier
                    .heightIn(max = 200.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                listMenuItems.forEach {
                    DropdownMenuItem(
                        onClick = {
                            isMenuVisible = false
                            if (it == "other") {
                                value = ""
                                readOnly = false
                            } else {
                                value = it
                                onValueChanged(it)
                                readOnly = true
                            }
                        }) {
                        Text(text = it)
                    }
                }
            }
        }
    }
}
