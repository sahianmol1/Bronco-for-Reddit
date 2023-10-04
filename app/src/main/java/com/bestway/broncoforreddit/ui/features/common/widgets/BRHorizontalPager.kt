package com.bestway.broncoforreddit.ui.features.common.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BRHorizontalPager(
    tabs: List<String>,
    pagerState: PagerState,
    pageContent: @Composable (pageNumber: Int) -> Unit
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        pageCount = tabs.size,
        state = pagerState,
        pageContent = pageContent
    )
}
