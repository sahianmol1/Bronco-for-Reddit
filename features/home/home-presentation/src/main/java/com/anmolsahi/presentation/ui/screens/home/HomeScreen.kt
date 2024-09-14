package com.anmolsahi.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.commonui.utils.rememberLifecycleEvent
import com.anmolsahi.commonui.utils.shareRedditPost
import com.anmolsahi.designsystem.uicomponents.BRHorizontalPager
import com.anmolsahi.designsystem.uicomponents.BRScrollableTabRow
import com.anmolsahi.designsystem.uicomponents.HomePage
import com.anmolsahi.designsystem.utils.showToast
import com.anmolsahi.homepresentation.R
import com.anmolsahi.presentation.ui.components.HomeScreenListings

// TODO: refactor this, probably need to use strategy design pattern
@SuppressWarnings("CyclomaticComplexMethod")
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onClick: (postId: String, postUrl: String) -> Unit,
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleEvent = rememberLifecycleEvent()

    val hotPosts by remember { homeViewModel.hotPosts }.collectAsStateWithLifecycle()

    val newPosts by remember { homeViewModel.newPosts }.collectAsStateWithLifecycle()

    val topPosts by remember { homeViewModel.topPosts }.collectAsStateWithLifecycle()

    val bestPosts by remember { homeViewModel.bestPosts }.collectAsStateWithLifecycle()

    val risingPosts by remember { homeViewModel.risingPosts }.collectAsStateWithLifecycle()

    val controversialPosts by remember {
        homeViewModel.controversialPosts
    }.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (hotPosts.data == null) {
            homeViewModel.getHotPosts()
        } else {
            homeViewModel.getHotPosts(silentUpdate = true)
        }

        if (newPosts.data == null) {
            homeViewModel.getNewPosts()
        } else {
            homeViewModel.getNewPosts(silentUpdate = true)
        }

        if (topPosts.data == null) {
            homeViewModel.getTopPosts()
        } else {
            homeViewModel.getTopPosts(silentUpdate = true)
        }

        if (bestPosts.data == null) {
            homeViewModel.getBestPosts()
        } else {
            homeViewModel.getBestPosts(silentUpdate = true)
        }

        if (risingPosts.data == null) {
            homeViewModel.getRisingsPosts()
        } else {
            homeViewModel.getRisingsPosts(silentUpdate = true)
        }

        if (controversialPosts.data == null) {
            homeViewModel.getControversialPosts()
        } else {
            homeViewModel.getControversialPosts(silentUpdate = true)
        }
    }

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            homeViewModel.checkAndForceRefreshPosts()
        }
    }

    val tabs by rememberSaveable {
        mutableStateOf(
            listOf(
                context.getString(R.string.hot),
                context.getString(R.string.title_new),
                context.getString(R.string.top),
                context.getString(R.string.best),
                context.getString(R.string.rising),
                context.getString(R.string.controversial),
            ),
        )
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { tabs.size },
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        BRScrollableTabRow(
            tabs = tabs,
            pagerState = pagerState,
        )
        BRHorizontalPager(
            modifier = Modifier
                .semantics {
                    contentDescription =
                        context.getString(R.string.content_description_home_screen_list)
                }
                .fillMaxSize(),
            pagerState = pagerState,
        ) { page ->
            when (page) {
                HomePage.HOT -> {
                    HomeScreenListings(
                        uiState = hotPosts,
                        onClick = onClick,
                        onImageFullScreenIconClick = onImageFullScreenIconClick,
                        onVideoFullScreenIconClick = onVideoFullScreenIconClick,
                        refreshData = { homeViewModel.getHotPosts(shouldRefreshData = true) },
                        onPullRefresh = {
                            homeViewModel.onPostsAvailableChipClick(HomePage.HOT)
                        },
                        loadMoreData = { nextPageKey ->
                            nextPageKey?.let {
                                homeViewModel.getHotPosts(nextPageKey = it)
                            }
                        },
                        onSaveIconClick = { postId ->
                            homeViewModel.onSaveIconClick(postId, HomePage.HOT) { isSaved ->
                                if (isSaved) {
                                    context.showToast(
                                        context.getString(R.string.post_saved_success),
                                    )
                                }
                            }
                        },
                        onShareIconClick = { postUrl -> shareRedditPost(postUrl, context) },
                    )
                }

                HomePage.NEW -> {
                    HomeScreenListings(
                        uiState = newPosts,
                        onClick = onClick,
                        onImageFullScreenIconClick = onImageFullScreenIconClick,
                        onVideoFullScreenIconClick = onVideoFullScreenIconClick,
                        refreshData = { homeViewModel.getNewPosts(true) },
                        onPullRefresh = {
                            homeViewModel.onPostsAvailableChipClick(HomePage.NEW)
                        },
                        loadMoreData = { nextPageKey ->
                            nextPageKey?.let {
                                homeViewModel.getNewPosts(nextPageKey = it)
                            }
                        },
                        onSaveIconClick = { postId ->
                            homeViewModel.onSaveIconClick(postId, HomePage.NEW) { isSaved ->
                                if (isSaved) {
                                    context.showToast(
                                        context.getString(R.string.post_saved_success),
                                    )
                                }
                            }
                        },
                        onShareIconClick = { postUrl -> shareRedditPost(postUrl, context) },
                    )
                }

                HomePage.TOP -> {
                    HomeScreenListings(
                        uiState = topPosts,
                        onClick = onClick,
                        onImageFullScreenIconClick = onImageFullScreenIconClick,
                        onVideoFullScreenIconClick = onVideoFullScreenIconClick,
                        refreshData = { homeViewModel.getTopPosts(true) },
                        onPullRefresh = {
                            homeViewModel.onPostsAvailableChipClick(HomePage.TOP)
                        },
                        loadMoreData = { nextPageKey ->
                            nextPageKey?.let {
                                homeViewModel.getTopPosts(nextPageKey = it)
                            }
                        },
                        onSaveIconClick = { postId ->
                            homeViewModel.onSaveIconClick(postId, HomePage.TOP) { isSaved ->
                                if (isSaved) {
                                    context.showToast(
                                        context.getString(R.string.post_saved_success),
                                    )
                                }
                            }
                        },
                        onShareIconClick = { postUrl -> shareRedditPost(postUrl, context) },
                    )
                }

                HomePage.BEST -> {
                    HomeScreenListings(
                        uiState = bestPosts,
                        onClick = onClick,
                        onImageFullScreenIconClick = onImageFullScreenIconClick,
                        onVideoFullScreenIconClick = onVideoFullScreenIconClick,
                        refreshData = { homeViewModel.getBestPosts(true) },
                        onPullRefresh = {
                            homeViewModel.onPostsAvailableChipClick(HomePage.BEST)
                        },
                        loadMoreData = { nextPageKey ->
                            nextPageKey?.let {
                                homeViewModel.getBestPosts(nextPageKey = it)
                            }
                        },
                        onSaveIconClick = { postId ->
                            homeViewModel.onSaveIconClick(postId, HomePage.BEST) { isSaved ->
                                if (isSaved) {
                                    context.showToast(
                                        context.getString(R.string.post_saved_success),
                                    )
                                }
                            }
                        },
                        onShareIconClick = { postUrl -> shareRedditPost(postUrl, context) },
                    )
                }

                HomePage.RISING -> {
                    HomeScreenListings(
                        uiState = risingPosts,
                        onClick = onClick,
                        onImageFullScreenIconClick = onImageFullScreenIconClick,
                        onVideoFullScreenIconClick = onVideoFullScreenIconClick,
                        refreshData = { homeViewModel.getRisingsPosts(true) },
                        onPullRefresh = {
                            homeViewModel.onPostsAvailableChipClick(HomePage.RISING)
                        },
                        loadMoreData = { nextPageKey ->
                            nextPageKey?.let {
                                homeViewModel.getRisingsPosts(nextPageKey = it)
                            }
                        },
                        onSaveIconClick = { postId ->
                            homeViewModel.onSaveIconClick(postId, HomePage.RISING) { isSaved ->
                                if (isSaved) {
                                    context.showToast(
                                        context.getString(R.string.post_saved_success),
                                    )
                                }
                            }
                        },
                        onShareIconClick = { postUrl -> shareRedditPost(postUrl, context) },
                    )
                }

                HomePage.CONTROVERSIAL -> {
                    HomeScreenListings(
                        uiState = controversialPosts,
                        onClick = onClick,
                        onImageFullScreenIconClick = onImageFullScreenIconClick,
                        onVideoFullScreenIconClick = onVideoFullScreenIconClick,
                        refreshData = { homeViewModel.getControversialPosts(true) },
                        onPullRefresh = {
                            homeViewModel.onPostsAvailableChipClick(HomePage.CONTROVERSIAL)
                        },
                        loadMoreData = { nextPageKey ->
                            nextPageKey?.let {
                                homeViewModel.getControversialPosts(nextPageKey = it)
                            }
                        },
                        onSaveIconClick = { postId ->
                            homeViewModel.onSaveIconClick(
                                postId,
                                HomePage.CONTROVERSIAL,
                            ) { isSaved ->
                                if (isSaved) {
                                    context.showToast(
                                        context.getString(R.string.post_saved_success),
                                    )
                                }
                            }
                        },
                        onShareIconClick = { postUrl -> shareRedditPost(postUrl, context) },
                    )
                }

                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = stringResource(R.string.unavailable))
                    }
                }
            }
        }
    }
}
