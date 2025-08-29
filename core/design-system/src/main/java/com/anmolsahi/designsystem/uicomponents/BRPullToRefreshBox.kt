package com.anmolsahi.designsystem.uicomponents

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@ExperimentalMaterial3Api
fun BRPullToRefreshBox(
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    contentAlignment: Alignment = Alignment.TopStart,
    onRefresh: () -> Unit,
    indicator: @Composable BoxScope.() -> Unit = {
        MyExpressiveIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = state,
        )
    },
    content: @Composable BoxScope.() -> Unit,
) {
    PullToRefreshBox(
        modifier = modifier,
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        contentAlignment = contentAlignment,
        indicator = indicator,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MyExpressiveIndicator(
    modifier: Modifier = Modifier,
    state: PullToRefreshState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    // Uses Crossfade to transition between a custom icon and the progress indicator.
    Crossfade(targetState = isRefreshing) { targetState ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .pullToRefresh(
                    state = state,
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                ) // Positions indicator based on gesture.
                .scale(state.distanceFraction.coerceIn(0f, 1f)), // Scales up with the pull.
            contentAlignment = Alignment.Center,
        ) {
            if (targetState) {
                // CircularProgressIndicator for the active refreshing state
                ContainedLoadingIndicator(
                    modifier = Modifier.align(Alignment.TopCenter)
                        .padding(top = 64.dp),
                )
            } else {
                // Custom, expressive icon for the pulling state
                Icon(
                    imageVector = Icons.Default.Cloud,
                    contentDescription = "Refresh",
                )
            }
        }
    }
}
