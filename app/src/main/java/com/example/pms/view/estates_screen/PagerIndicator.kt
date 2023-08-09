package com.example.pms.view.estates_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pms.ui.theme.doublelightblue

@Composable
fun PagerIndicator(
    current_index: Int,
    length: Int
) {


    Row {
        for (i in 0 until length) {

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .padding(start = 4.dp, end = 4.dp)
                    .background(
                        color = if (current_index == i) doublelightblue else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
            )


        }

    }
}
