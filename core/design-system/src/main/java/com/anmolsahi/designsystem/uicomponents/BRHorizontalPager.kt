package com.anmolsahi.designsystem.uicomponents

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
    pageContent: @Composable PagerScope.(page: HomePage) -> Unit,
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        pageContent = {
            pageContent(HomePage.entries[it])
        },
    )
}

enum class HomePage(val id: Int) {
    HOT(id = 0),
    NEW(id = 1),
    TOP(id = 2),
    BEST(id = 3),
    RISING(id = 4),
    CONTROVERSIAL(id = 5),
}
