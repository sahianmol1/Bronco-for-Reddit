package com.anmolsahi.designsystem.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

fun slideInFromBottomTransition(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { screenHeight ->
            screenHeight
        },
    )
}

fun slideInFromTopTransition(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { screenHeight ->
            -screenHeight
        },
    )
}

fun slideOutToBottomTransition(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { screenHeight ->
            -screenHeight
        },
    )
}

fun slideOutToTopTransition(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { screenHeight ->
            screenHeight
        },
    )
}
