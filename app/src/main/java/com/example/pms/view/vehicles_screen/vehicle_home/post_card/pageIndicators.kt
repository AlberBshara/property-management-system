package com.example.pms.view.vehicles_screen.vehicle_home.post_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(
    current_index: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .padding(start = 4.dp, end = 4.dp)
                .background(
                    color = if (current_index == 0) Color.Green else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
        )
        Box(
            modifier = Modifier
                .size(12.dp)
                .padding(start = 4.dp, end = 4.dp)
                .background(
                    color = if (current_index == 1) Color.Green else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
        )
        Box(
            modifier = Modifier
                .size(12.dp)
                .padding(start = 4.dp, end = 4.dp)
                .background(
                    color = if (current_index == 2) Color.Green else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
        )
    }
}

