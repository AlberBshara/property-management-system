package com.example.pms.view.vehicles_screen.vehicle_home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.pms.R

@Composable
fun SearchBar(
    query: String,
    onQueryListener: (String) -> Unit,
    onCancelListener : () -> Unit
) {


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
                    cursorColor = Color.Green,
                    focusedLabelColor = Color.Green,
                    focusedIndicatorColor = Color.Green
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

                }
            )
        }
    }
}




