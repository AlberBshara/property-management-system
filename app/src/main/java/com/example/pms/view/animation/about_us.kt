package com.example.pms.view.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.example.pms.R

@Composable
fun AboutUsAnimation(
    isAnimating: Boolean,
    modifier: Modifier = Modifier
) {
    if (isAnimating) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec
                .RawRes(R.raw.about_us)
        )

        val animationState by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = isAnimating
        )

        LottieAnimation(
            composition = composition,
            progress = animationState,
            modifier = modifier
        )
    }
}