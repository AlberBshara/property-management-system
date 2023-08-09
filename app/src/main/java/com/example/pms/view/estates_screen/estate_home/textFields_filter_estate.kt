package com.example.pms.view.estates_screen.estate_home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.lightblue
import com.example.pms.ui.theme.transparentGray
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeConstants

@Composable
fun TextFiledDropDownOutLined(
    onValueChanged: (String) -> Unit,
    label: Int,
    listMenuItems: EstateHomeConstants.Companion.FilteringType,
    modifier: Modifier = Modifier,
    textState: String = ""
) {

    var isMenuVisible by remember {
        mutableStateOf(false)
    }
    var value by remember {
        mutableStateOf("")
    }
    var readOnly by remember {
        mutableStateOf(true)
    }

    value = textState
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp),
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
                focusedBorderColor = lightblue,
                unfocusedBorderColor = transparentGray,
                cursorColor = lightblue
            ),
            shape = RoundedCornerShape(0.dp),
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
                listMenuItems.items.forEach {
                    val item = stringResource(id = it)
                    DropdownMenuItem(
                        onClick = {
                            isMenuVisible = false
                            if (item == "other") {
                                value = ""
                                readOnly = false
                            } else {
                                value = item
                                onValueChanged(item)
                                readOnly = true
                            }
                        }) {
                        Text(text = item)
                    }
                }

            }
        }


    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFiledOutLined(
    onValueChanged: (String) -> Unit,
    label: Int,
    modifier: Modifier,
    leadingIcon: Int,
    keyboardType: KeyboardType,
    keyboardOptions: ImeAction,
    enable: Boolean = true,
    textState: String = ""
) {

    val keyboard = LocalSoftwareKeyboardController.current

    var value by remember {
        mutableStateOf("")
    }
    value = textState
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp),
        value = value,
        onValueChange = {
            value = it
            onValueChanged(it)
        },

        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = transparentGray,
            focusedBorderColor = lightblue,
            unfocusedBorderColor = transparentGray,
            cursorColor = lightblue
        ),
        shape = RoundedCornerShape(0.dp),
        label = {
            Text(
                text = stringResource(id = label),
                color = Color.DarkGray,
                style = MaterialTheme.typography.caption
            )
        },

        singleLine = true,

        leadingIcon = {
            Icon(painter = painterResource(id = leadingIcon), contentDescription = null)
        },
        keyboardActions = KeyboardActions(
            onDone = { keyboard?.hide() }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = keyboardOptions
        ),
        enabled = enable,


        )


}