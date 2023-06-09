package com.example.pms.view.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.transparent_p

@Composable
fun CardSocialMedia(
    facebookClickable: () -> Unit,
    instagramClickable: () -> Unit,
    twitterClickable: () -> Unit
) {
    Card(
        backgroundColor = transparent_p,
        modifier = Modifier.clip(RoundedCornerShape(100)),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(painter = painterResource(id = R.drawable.facebook_ic),
                contentDescription = "",
                modifier = Modifier
                    .clickable { facebookClickable() }
                    .size(30.dp)
                    .clip(shape = CircleShape))
            Image(painter = painterResource(id = R.drawable.instagram_ic),
                contentDescription = "",
                modifier = Modifier
                    .clickable { instagramClickable() }
                    .size(30.dp)
                    .clip(shape = CircleShape))
            Image(painter = painterResource(id = R.drawable.twitter_ic),
                contentDescription = "",
                modifier = Modifier
                    .clickable { twitterClickable() }
                    .size(30.dp)
                    .clip(shape = CircleShape))
        }
    }
}