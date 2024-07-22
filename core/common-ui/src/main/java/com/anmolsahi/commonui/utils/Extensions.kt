package com.anmolsahi.commonui.utils

import androidx.compose.foundation.lazy.LazyListState

suspend fun LazyListState.scrollToTop() {
    scrollToItem(0)
}

suspend fun LazyListState.animateScrollToTop() {
    animateScrollToItem(0)
}
