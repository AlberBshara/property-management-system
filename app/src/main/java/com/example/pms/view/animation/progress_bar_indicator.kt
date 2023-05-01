package com.example.pms.view.animation


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.pms.R

@SuppressLint("SuspiciousIndentation")
@Composable
fun ProgressAnimatedBar(
    isLoading : Boolean
) {
    if (isLoading) {
      val composition by rememberLottieComposition(
          spec = LottieCompositionSpec
              .RawRes(R.raw.loading_indiactor)
      )

        val animationState by animateLottieCompositionAsState(
            composition = composition ,
            iterations = LottieConstants.IterateForever,
            isPlaying = isLoading,
            speed = 1f,
            restartOnPlay = false
        )
            LottieAnimation(composition = composition,
                progress = animationState ,
            modifier = Modifier.size(150.dp))
    }
}