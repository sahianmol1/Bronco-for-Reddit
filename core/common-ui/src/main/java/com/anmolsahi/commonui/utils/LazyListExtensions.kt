package com.anmolsahi.commonui.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.withContext

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun LazyListState.ScrollHelper(resetScroll: Boolean, postScroll: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = resetScroll) {
        if (resetScroll) {
            withContext(coroutineScope.coroutineContext) { animateScrollToItem(0) }
            postScroll()
        }
    }
}

suspend fun LazyListState.scrollToTop() {
    scrollToItem(0)
}

suspend fun LazyListState.animateScrollToTop() {
    animateScrollToItem(0)
}
