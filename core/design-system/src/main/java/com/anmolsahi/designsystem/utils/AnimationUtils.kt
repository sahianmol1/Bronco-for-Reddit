package com.anmolsahi.designsystem.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.slideInVertically

fun slideInFromBottomTransition(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { screenHeight ->
            screenHeight / 2
        },
    )
}
