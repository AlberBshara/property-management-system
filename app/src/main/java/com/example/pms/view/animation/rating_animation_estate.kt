package com.example.pms.view.animation


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.example.pms.R

@SuppressLint("SuspiciousIndentation")
@Composable
fun RatingAnimationEstate(
    isAnimating: Boolean,
    modifier: Modifier
) {
    if (isAnimating) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec
                .RawRes(R.raw.rating_animation_estate2)
        )

        val animationState by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = isAnimating,
            speed = 1f,
            restartOnPlay = false
        )
        LottieAnimation(
            composition = composition,
            progress = animationState,
            modifier = modifier
        )
    }
}