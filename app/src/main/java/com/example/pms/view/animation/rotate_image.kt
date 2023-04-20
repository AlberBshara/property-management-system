package com.example.pms.view.animation


import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import com.example.pms.R

@Composable
fun RotateImage(
    modifier: Modifier,
    image: Int = R.drawable.logo,
) {


    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing)
        )
    )

    Image(
        painter = painterResource(
            id = image
        ),
        contentDescription = "",
        modifier = modifier
            .rotate(angle)
    )

}


