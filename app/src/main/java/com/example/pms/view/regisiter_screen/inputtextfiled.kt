package com.example.pms.view.regisiter_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.iconsColor

@Composable
fun InputTextFiled(
    title: String,
    icon: Int,
    singleLine: Boolean = true,
    keyboardOption: KeyboardOptions,
    onValueChanged: (String) -> Unit,
    onIconListener: () -> Unit = {},
    isError: Boolean
) {

    val text = rememberSaveable {
        mutableStateOf("")
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        isError = isError,
        singleLine = singleLine,
        keyboardOptions = keyboardOption,
        label = { Text(text = title) },
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
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.clickable {
                    onIconListener()
                }
            )
        },

        value = text.value,
        onValueChange = {
            text.value = it
            onValueChanged(it)
        }
    )
}


@Composable
fun InputPassword(
    title: Int = R.string.password,
    onValueChanged: (String) -> Unit,
    isError: Boolean
) {
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation: VisualTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    TextField(
        value = password,
        isError = isError,
        onValueChange = {
            password = it
            onValueChanged(it)
        },
        label = { Text(stringResource(id = title)) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
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
                painter = painterResource(id = R.drawable.password_vector),
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.baseline_remove_red_eye_24) else
                    painterResource(id = R.drawable.hide_password)
                Icon(painter = image, contentDescription = null)
            }
        })
}