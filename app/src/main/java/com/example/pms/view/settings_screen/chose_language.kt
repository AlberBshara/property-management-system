package com.example.pms.view.settings_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pms.R

@Composable
fun ChoseLanguage(
    selectedLanguage: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.change_language),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            style = MaterialTheme.typography.caption
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                .clickable {
                    selectedLanguage("en")
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.america),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.english),
                color = Color.Black,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W100
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                .clickable {
                    selectedLanguage("ar")
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.syria),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.arabic),
                color = Color.Black,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.W100
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}