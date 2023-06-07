package com.example.pms.view.utils

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.pms.view.animation.ProgressAnimatedBar

@Composable
fun DialogLoading(
    isLoading: Boolean
) {
    if (isLoading) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ProgressAnimatedBar(
                    isLoading = true,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}