package com.bestway.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bestway.design_system.ui_components.BRLinearProgressIndicator
import com.bestway.design_system.utils.slideInFromBottomTransition
import com.bestway.home_presentation.R
import com.bestway.presentation.model.RedditPostUiModel
import com.bestway.presentation.ui.screens.home.PostsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenListings(
    uiState: PostsUiState,
    onClick: (redditPostUiModel: RedditPostUiModel) -> Unit,
    refreshData: () -> Unit,
    loadMoreData: (nextPageKey: String?) -> Unit,
) {
    val pullRefreshState = rememberPullToRefreshState()
    val lazyListState = rememberLazyListState()
    val list by remember(uiState) { mutableStateOf(uiState.data.orEmpty()) }
    var shouldShowPagingLoading by rememberSaveable { mutableStateOf(false) }

    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            pullRefreshState.startRefresh()
            refreshData()
        }
    }

    if (uiState.isDataRefreshed) {
        pullRefreshState.endRefresh()
    }

    val firstVisibleItemIndex by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex }
    }

    if (lazyListState.isScrollingDown() && firstVisibleItemIndex == list.size - 5) {
        shouldShowPagingLoading = true
        loadMoreData(list.last().after)
    }

    if (uiState.pageDataLoadFinished) {
        shouldShowPagingLoading = false
    }

    Box(
        Modifier.nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        AnimatedVisibility(visible = list.isNotEmpty(), enter = slideInFromBottomTransition()) {
            LazyColumn(
                state = lazyListState
            ) {
                itemsIndexed(
                    items = list,
                    key = { _, item ->
                        item.id
                    },
                    contentType = { _, _ ->
                        "reddit_post"
                    }
                ) { index, item ->
                    PostComponent(
                        modifier =
                        when (index) {
                            list.size - 1 -> Modifier.navigationBarsPadding()
                            else -> Modifier
                        },
                        redditPostUiModel = item,
                        onClick = onClick
                    )
                }
                if (shouldShowPagingLoading) {
                    item("last_item", contentType = "loading_indicator") {
                        LinearProgressIndicator()
                    }
                }
            }
        }

        // Show error screen
        AnimatedVisibility(
            visible = !uiState.errorMessage.isNullOrBlank() && list.isEmpty(),
            enter = slideInFromBottomTransition()
        ) {
            var showLogs by rememberSaveable { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TODO: Code Cleanup
                Text(
                    modifier = Modifier.clickable { showLogs = !showLogs },
                    text =
                    if (!showLogs)
                        stringResource(R.string.uh_oh_something_went_wrong) + " Learn More"
                    else
                        stringResource(R.string.uh_oh_something_went_wrong) +
                                " Learn More /n ${uiState.errorMessage}",
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        if (uiState.isLoading) {
            BRLinearProgressIndicator()
        }

        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter),
            state = pullRefreshState,
            containerColor = if (hidePullRefreshContainer(pullRefreshState)) Color.Transparent
            else PullToRefreshDefaults.containerColor
        )
    }
}

@Composable
private fun LazyListState.isScrollingDown(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex < firstVisibleItemIndex
            } else {
                previousScrollOffset < firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@OptIn(ExperimentalMaterial3Api::class)
private fun hidePullRefreshContainer(state: PullToRefreshState) =
    state.progress == 0f && !state.isRefreshing
