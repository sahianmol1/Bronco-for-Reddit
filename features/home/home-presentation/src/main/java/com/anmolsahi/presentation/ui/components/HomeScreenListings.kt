package com.anmolsahi.presentation.ui.components

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.anmolsahi.commonui.components.NewPostsAvailableComponent
import com.anmolsahi.commonui.components.PostComponent
import com.anmolsahi.commonui.utils.animateScrollToTop
import com.anmolsahi.designsystem.uicomponents.BRLinearProgressIndicator
import com.anmolsahi.designsystem.uicomponents.BRScrollToTopButton
import com.anmolsahi.designsystem.utils.slideInFromBottom
import com.anmolsahi.designsystem.utils.slideInFromTop
import com.anmolsahi.designsystem.utils.slideOutToBottom
import com.anmolsahi.designsystem.utils.slideOutToTop
import com.anmolsahi.homepresentation.R
import com.anmolsahi.presentation.ui.screens.home.PostsUiState
import kotlinx.coroutines.launch

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
) {
    val pullRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val list by remember(uiState) { mutableStateOf(uiState.data.orEmpty()) }
    val configuration = LocalConfiguration.current
    val scrollToTopButtonModifier =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Modifier.navigationBarsPadding()
        } else {
            Modifier
        }

    LaunchedEffect(uiState.isPullRefreshLoading) {
        if (!uiState.isPullRefreshLoading) {
            pullRefreshState.endRefresh()
        }

        if (uiState.isPullRefreshLoading) {
            lazyListState.animateScrollToTop()
            pullRefreshState.startRefresh()
            refreshData()
        }
    }

    LaunchedEffect(pullRefreshState.isRefreshing) {
        if (pullRefreshState.isRefreshing && !uiState.isPullRefreshLoading) {
            onPullRefresh()
        }
    }

    LaunchedEffect(lazyListState.canScrollForward) {
        if (!lazyListState.canScrollForward && uiState.data?.isNotEmpty() == true) {
            loadMoreData(list.last().after)
        }
    }

    Box(
        modifier
            .nestedScroll(pullRefreshState.nestedScrollConnection),
        contentAlignment = Alignment.BottomEnd,
    ) {
        if (list.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .testTag("home_screen_list"),
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

        AnimatedVisibility(
            visible = lazyListState.canScrollBackward,
            enter = slideInFromBottom(),
            exit = slideOutToBottom(),
        ) {
            BRScrollToTopButton(
                modifier = scrollToTopButtonModifier,
            ) {
                coroutineScope.launch { lazyListState.animateScrollToTop() }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter),
            state = pullRefreshState,
            containerColor =
            if (hidePullRefreshContainer(pullRefreshState)) {
                Color.Transparent
            } else {
                PullToRefreshDefaults.containerColor
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun hidePullRefreshContainer(state: PullToRefreshState) =
    state.progress == 0f && !state.isRefreshing
