package com.anmolsahi.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.anmolsahi.commonui.components.NewPostsAvailableComponent
import com.anmolsahi.commonui.components.PostComponent
import com.anmolsahi.commonui.utils.ScrollHelper
import com.anmolsahi.commonui.utils.animateScrollToTop
import com.anmolsahi.designsystem.uicomponents.BRLinearProgressIndicator
import com.anmolsahi.designsystem.uicomponents.BRPullToRefreshBox
import com.anmolsahi.designsystem.utils.slideInFromBottom
import com.anmolsahi.designsystem.utils.slideInFromTop
import com.anmolsahi.designsystem.utils.slideOutToTop
import com.anmolsahi.homepresentation.R
import com.anmolsahi.presentation.ui.screens.home.PostsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenListings(
    uiState: PostsUiState,
    onClick: (postId: String, postUrl: String) -> Unit,
    refreshData: () -> Unit,
    loadMoreData: (nextPageKey: String?) -> Unit,
    onSaveIconClick: (postId: String) -> Unit,
    onShareIconClick: (postUrl: String) -> Unit,
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
    onPullRefresh: () -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
    resetScroll: Boolean,
    postResetScroll: () -> Unit,
) {
    val pullRefreshState = rememberPullToRefreshState()
    val lazyListState = rememberLazyListState()
    lazyListState.ScrollHelper(resetScroll = resetScroll, postResetScroll)
    val list by remember(uiState) { mutableStateOf(uiState.data.orEmpty()) }

    LaunchedEffect(uiState.isPullRefreshLoading) {
        if (uiState.isPullRefreshLoading) {
            lazyListState.animateScrollToTop()
            refreshData()
        }
    }

    LaunchedEffect(lazyListState.canScrollForward) {
        if (!lazyListState.canScrollForward && uiState.data?.isNotEmpty() == true) {
            loadMoreData(list.last().after)
        }
    }

    BRPullToRefreshBox(
        isRefreshing = uiState.isPullRefreshLoading,
        state = pullRefreshState,
        onRefresh = onPullRefresh,
    ) {
        Box(
            modifier,
            contentAlignment = Alignment.BottomEnd,
        ) {
            if (list.isNotEmpty()) {
                LazyColumn(
                    state = lazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                ) {
                    itemsIndexed(
                        items = list,
                        key = { _, item ->
                            item.id
                        },
                        contentType = { _, _ ->
                            "reddit_post"
                        },
                    ) { _, item ->
                        PostComponent(
                            redditPostUiModel = item,
                            onClick = onClick,
                            onSaveIconClick = onSaveIconClick,
                            onShareIconClick = onShareIconClick,
                            onVideoFullScreenIconClick = onVideoFullScreenIconClick,
                            onImageFullScreenIconClick = onImageFullScreenIconClick,
                        )
                    }

                    item("paging_loading_indicator", contentType = "loading_indicator") {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
            }

            // Show error screen
            AnimatedVisibility(
                visible = !uiState.errorMessage.isNullOrBlank() && list.isEmpty(),
                enter = slideInFromBottom(),
            ) {
                var showLogs by rememberSaveable { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // TODO: Code Cleanup
                    Text(
                        modifier = Modifier.clickable { showLogs = !showLogs },
                        text =
                        if (!showLogs) {
                            stringResource(R.string.uh_oh_something_went_wrong) + " Learn More"
                        } else {
                            stringResource(R.string.uh_oh_something_went_wrong) +
                                " Learn More /n ${uiState.errorMessage}"
                        },
                        textDecoration = TextDecoration.Underline,
                    )
                }
            }

            if (uiState.isLoading) {
                BRLinearProgressIndicator()
            }

            AnimatedVisibility(
                uiState.areNewPostsAvailable,
                enter = slideInFromTop(),
                exit = slideOutToTop(),
            ) {
                Column {
                    NewPostsAvailableComponent(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        onClick = onPullRefresh,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
