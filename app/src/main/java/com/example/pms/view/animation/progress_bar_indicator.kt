package com.example.pms.view.animation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.example.pms.R

@Composable
fun ProgressAnimatedBar(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec
                .RawRes(R.raw.loading_indiactor)
        )

        val animationState by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = isLoading,
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