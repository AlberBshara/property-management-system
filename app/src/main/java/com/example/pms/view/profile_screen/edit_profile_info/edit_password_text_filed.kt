package com.example.pms.view.profile_screen.edit_profile_info


import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.pms.ui.theme.iconsColor
import com.example.pms.ui.theme.lightBlue

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditProfileTextFieldPassword(
    modifier : Modifier,
    isError:Boolean,
    singleLine:Boolean,
    label:String,
    leadingIcon:Int,
    keyboardOptions: ImeAction,
    onValueChange:(String)->Unit,
    textState:String=""
){


    var text by remember {
        mutableStateOf("")
    }
    text=textState
    val keyboard=LocalSoftwareKeyboardController.current
    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text=it
            onValueChange(it)
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
        keyboardActions = KeyboardActions(onDone = {keyboard?.hide()}),
        visualTransformation =  if(showPassword){
            VisualTransformation.None
        }else{
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