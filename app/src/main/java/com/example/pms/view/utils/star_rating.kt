package com.example.pms.view.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pms.ui.theme.darkYellow

@Composable
fun RatingBar(
    size: Dp,
    maxRating: Int = 5,
    rating: Int,
    onRatingSelected: (Int) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        for (i in 1..maxRating) {
            val isSelected = i <= rating
            StarIcon(
                isSelected = isSelected,
                onClick = { onRatingSelected(i) },
                size = size
            )
        }
    }
}

@Composable
fun StarIcon(
    isSelected: Boolean,
    onClick: () -> Unit,
    size: Dp = 16.dp
) {
    val starColor = if (isSelected) darkYellow else Color.Gray

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(2.dp)
            .clip(CircleShape)
            .size(size)
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = starColor
        )
    }
}