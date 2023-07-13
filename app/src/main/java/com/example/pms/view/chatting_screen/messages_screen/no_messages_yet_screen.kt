package com.example.pms.view.chatting_screen.messages_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pms.view.animation.AnimatedStartChatting
import com.example.pms.R

@Composable
fun NoMessagesYetScreen(
    modifier: Modifier = Modifier,
    isAnimating: Boolean
) {
    if (isAnimating) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            AnimatedStartChatting(
                modifier = Modifier
                    .size(300.dp),
                isAnimating = true
            )
            Text(
                text = stringResource(id = R.string.start_chatting),
                color = Color.LightGray,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}