package com.anmolsahi.designsystem.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.unit.IntOffset

private const val DEFAULT_ANIMATION_DURATION = 400

private val tween: FiniteAnimationSpec<IntOffset> = tween(DEFAULT_ANIMATION_DURATION)

fun slideInFromBottom(): EnterTransition {
    return slideInVertically(
        animationSpec = tween,
        initialOffsetY = { screenHeight ->
            screenHeight
        },
    )
}

fun slideInFromTop(): EnterTransition {
    return slideInVertically(
        animationSpec = tween,
        initialOffsetY = { screenHeight ->
            -screenHeight
        },
    )
}

fun slideOutToBottom(): ExitTransition {
    return slideOutVertically(
        animationSpec = tween,
        targetOffsetY = { screenHeight ->
            screenHeight
        },
    )
}

fun slideOutToTop(): ExitTransition {
    return slideOutVertically(
        animationSpec = tween,
        targetOffsetY = { screenHeight ->
            -screenHeight * 2
        },
    )
}

fun slideInFromRight(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween,
        initialOffsetX = { screenWidth ->
            screenWidth
        },
    )
}

fun slideOutToRight(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween,
        targetOffsetX = { screenWidth ->
            screenWidth
        },
    )
}

fun slideInFromLeft(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween,
        initialOffsetX = { screenWidth ->
            -screenWidth
        },
    )
}

fun slideOutToLeft(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween,
        targetOffsetX = { screenWidth ->
            -screenWidth
        },
    )
}
