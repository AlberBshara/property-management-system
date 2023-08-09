package com.example.pms.view.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.lightBlue
import com.example.pms.view.animation.NoResultAnimation

@Composable
fun NoResultPage(
    modifier: Modifier = Modifier,
    isShowing: Boolean,
    size: Dp = 300.dp,
    goBack: () -> Unit
) {
   if (isShowing) {
       Column(
           modifier = modifier,
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           NoResultAnimation(
               isAnimating = true,
               modifier = Modifier
                   .size(size)
                   .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 30.dp)
           )

           Box(
               modifier = Modifier
                   .padding(10.dp)
                   .fillMaxWidth(),
               contentAlignment = Alignment.Center
           ) {
               Button(
                   onClick = {
                       goBack()
                   },
                   colors = ButtonDefaults.buttonColors(
                       backgroundColor = lightBlue,
                       disabledBackgroundColor = lightBlue
                   )
               ) {
                   Text(
                       text = stringResource(id = R.string.go_back),
                       color = Color.White,
                       fontWeight = FontWeight.Bold,
                       modifier = Modifier
                           .padding(5.dp)
                   )
               }
           }

       }
   }
}