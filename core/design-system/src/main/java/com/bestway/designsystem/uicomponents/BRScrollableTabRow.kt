package com.bestway.designsystem.uicomponents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BRScrollableTabRow(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    pagerState: PagerState,
) {
    val coroutineScope = rememberCoroutineScope()

    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}
