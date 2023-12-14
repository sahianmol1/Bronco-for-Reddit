package com.bestway.design_system.ui_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BRHorizontalPager(
    pagerState: PagerState,
    pageContent: @Composable PagerScope.(page: Int) -> Unit
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        pageContent = pageContent
    )
}
