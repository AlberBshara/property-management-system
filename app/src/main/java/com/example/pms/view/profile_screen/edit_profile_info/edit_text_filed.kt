package com.example.pms.view.profile_screen.edit_profile_info

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.example.pms.ui.theme.lightBlue


@Composable
fun EditProfileTextFields(
    modifier: Modifier = Modifier,
    isError: Boolean,
    singleLine: Boolean,
    label: String,
    leadingIcon: Int,
    keyboardOptions: ImeAction,
    onValueChange: (String) -> Unit,
    textState: String = ""
) {

    var test by remember {
        mutableStateOf("")
    }

    test = textState

    OutlinedTextField(
        value = test,
        onValueChange = {
            test = it
            onValueChange(it)
        },
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Gray,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = lightBlue,
            unfocusedIndicatorColor = lightBlue,
            disabledIndicatorColor = lightBlue,
            cursorColor = lightBlue,
            focusedLabelColor = lightBlue,
            unfocusedLabelColor = lightBlue
        ),
        isError = isError,
        singleLine = singleLine,

        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = "",
                tint = lightBlue,
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = keyboardOptions,
        )
    )
}