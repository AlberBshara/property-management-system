package com.example.pms.view.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.pms.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: String,
    onQueryListener: (String) -> Unit,
    onCancelListener: () -> Unit,
    onDoneListener: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
                .clip(shape = RoundedCornerShape(24.dp)),
            elevation = 5.dp,
            contentColor = Color.Transparent
        ) {
            TextField(
                value = query,
                onValueChange = {
                    onQueryListener(it)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(24.dp)),
                placeholder = { Text(text = stringResource(id = R.string.search)) },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    focusedIndicatorColor = Color.Blue
                ),
                textStyle = TextStyle(
                    color = Color.Black
                ),
                trailingIcon = {
                    Icon(imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            onCancelListener()
                        })

                },
                keyboardActions = KeyboardActions(onDone = {
                    onDoneListener()
                    keyboardController?.hide()
                })
            )
        }
    }
}




