package com.example.pms.view.chatting_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pms.model.ChattingItemData
import com.example.pms.R
import com.example.pms.ui.theme.lightGreen
import com.example.pms.ui.theme.rainbowColors

@Composable
fun ChattingItemCard(
    onClinkListener: (ChattingItemData) -> Unit,
    chattingItemData: ChattingItemData,
    onCallingPhoneListener: (phoneNumber: String) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClinkListener(chattingItemData)
            }
    ) {

        if (chattingItemData.imageUrl != null) {
            AsyncImage(
                model = chattingItemData.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .border(border = BorderStroke(4.dp, Brush.sweepGradient(rainbowColors)))
                    .padding(4.dp)
                    .clip(shape = CircleShape)

            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.person_profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .border(border = BorderStroke(2.dp, Brush.sweepGradient(rainbowColors)),
                    shape = CircleShape)
                    .padding(4.dp)
                    .clip(shape = CircleShape)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = chattingItemData.username,
                textAlign = TextAlign.Start,
                color = Color.Black,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = chattingItemData.lastMessage
                    ?: stringResource(id = R.string.no_messages),
                color = Color.Gray,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Start
            )
        }
        IconButton(onClick = {
            onCallingPhoneListener(chattingItemData.phoneNumber)
        }) {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = null,
                tint = lightGreen
            )
        }
    }
}







