package com.example.pms.view.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.example.pms.R


@Composable
fun AnimatedStartChatting(
    modifier: Modifier = Modifier,
    isAnimating: Boolean
) {
    if (isAnimating) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec
                .RawRes(R.raw.chatting)
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