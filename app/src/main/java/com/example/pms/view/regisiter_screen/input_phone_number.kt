package com.example.pms.view.regisiter_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor


@Composable
fun InputPhoneWithCCP(
    onValueChanged: (phoneNumber: String, countryCode: String) -> Unit,
) {

    val countyCodes = stringArrayResource(id = R.array.country_codes)

    var userPhoneNumber by remember {
        mutableStateOf("")
    }
    var selectedCode by remember {
        mutableStateOf("000")
    }

    var droppedList by remember {
        mutableStateOf(false)
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone
        ),
        label = { Text(text = stringResource(id = R.string.phone)) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Gray,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = iconsColor,
            unfocusedIndicatorColor = iconsColor,
            disabledIndicatorColor = iconsColor,
            cursorColor = iconsColor,
            focusedLabelColor = iconsColor

        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.drop),
                contentDescription = "",
                modifier = Modifier.clickable {
                    droppedList = !droppedList
                }
            )
        },
        value = userPhoneNumber,
        onValueChange = {
            userPhoneNumber = it
            onValueChanged(userPhoneNumber, selectedCode)
        }
    )

    Box {
        DropdownMenu(expanded = droppedList,
            onDismissRequest = {
                droppedList = false
            }) {
            countyCodes.forEach {
                DropdownMenuItem(onClick = {
                    selectedCode = it
                    droppedList = false
                }) {
                    Text(text = it)
                }
            }
        }
    }


}



