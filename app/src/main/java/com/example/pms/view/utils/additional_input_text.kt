package com.example.pms.view.utils


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.pms.ui.theme.lightBlue
import com.example.pms.ui.theme.transparentGray
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.pms.ui.theme.iconsColor


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFiledOutLined(
    onValueChanged: (String) -> Unit,
    label: Int,
    modifier: Modifier,
    leadingIcon: Int,
    keyboardType: KeyboardType,
    enable: Boolean = true
) {

    val keyboard = LocalSoftwareKeyboardController.current
    var value by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            value = it
            onValueChanged(it)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = transparentGray,
            focusedBorderColor = lightBlue,
            unfocusedBorderColor = transparentGray,
            cursorColor = lightBlue
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
        leadingIcon = {
            Icon(painter = painterResource(id = leadingIcon), contentDescription = null)
        },
        keyboardActions = KeyboardActions(
            onDone = { keyboard?.hide() }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        enabled = enable
    )
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditProfileTextFieldPassword(
    modifier: Modifier,
    isError: Boolean,
    singleLine: Boolean,
    label: String,
    leadingIcon: Int,
    keyboardOptions: ImeAction,
    onValueChange: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    val keyboard = LocalSoftwareKeyboardController.current
    var showPassword by remember { mutableStateOf(value = false) }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        modifier = modifier.onFocusChanged {
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Gray,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = lightBlue,
            unfocusedIndicatorColor = lightBlue,
            disabledIndicatorColor = lightBlue,
            cursorColor = iconsColor,
            focusedLabelColor = lightBlue,
            unfocusedLabelColor = Color.Black
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
        ),
        keyboardActions = KeyboardActions(onDone = { keyboard?.hide() }),
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "hide_password"
                    )
                }
            } else {
                IconButton(
                    onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "hide_password"
                    )
                }
            }
        }
    )
}