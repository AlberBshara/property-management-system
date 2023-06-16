package com.example.pms.view.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.lightBlue

@Composable
fun RefreshScreen(
    needRefresh: Boolean,
    onReloadListener: () -> Unit
) {
  if (needRefresh) {
      Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
              .fillMaxSize()
              .background(color = Color.White)
      ) {
          Image(
              painter = painterResource(id = R.drawable.no_internet),
              contentDescription = null,
              contentScale = ContentScale.Crop,
              modifier = Modifier
                  .size(260.dp)
                  .padding(10.dp)
          )
          Text(
              text = stringResource(id = R.string.no_internet),
              fontWeight = FontWeight.Bold,
              color = Color.Black,
              modifier = Modifier
                  .padding(10.dp)
                  .fillMaxWidth(),
              textAlign = TextAlign.Center,
              style = MaterialTheme.typography.subtitle1
          )
          Text(
              text = stringResource(id = R.string.try_these_steps),
              color = Color.Black,
              modifier = Modifier
                  .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                  .fillMaxWidth(),
              textAlign = TextAlign.Center,
              style = MaterialTheme.typography.subtitle2
          )

          Text(
              text = stringResource(id = R.string.check_wifi),
              color = Color.Black,
              modifier = Modifier
                  .padding(10.dp)
                  .fillMaxWidth(),
              textAlign = TextAlign.Center,
              style = MaterialTheme.typography.subtitle2
          )

          Box(
              modifier = Modifier
                  .padding(10.dp)
                  .fillMaxWidth(),
              contentAlignment = Alignment.Center
          ) {
              Button(
                  onClick = {
                      onReloadListener()
                  },
                  colors = ButtonDefaults.buttonColors(
                      backgroundColor = lightBlue,
                      disabledBackgroundColor = lightBlue
                  )
              ) {
                  Text(
                      text = stringResource(id = R.string.reload),
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