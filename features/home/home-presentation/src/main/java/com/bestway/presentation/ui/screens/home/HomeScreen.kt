package com.bestway.presentation.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bestway.design_system.ui_components.BRHorizontalPager
import com.bestway.design_system.ui_components.BRScrollableTabRow
import com.bestway.home_presentation.R
import com.bestway.presentation.model.RedditPostUiModel
import com.bestway.presentation.ui.components.HomeScreenListings

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClick: (redditPostUiModel: RedditPostUiModel) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val hotPosts = homeViewModel.hotPosts.collectAsStateWithLifecycle()
    val newPosts = homeViewModel.newPosts.collectAsStateWithLifecycle()
    val topPosts = homeViewModel.topPosts.collectAsStateWithLifecycle()
    val bestPosts = homeViewModel.bestPosts.collectAsStateWithLifecycle()
    val risingPosts = homeViewModel.risingPosts.collectAsStateWithLifecycle()
    val controversialPosts = homeViewModel.controversialPosts.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (hotPosts.value.data == null) {
            homeViewModel.getHotPosts()
        }

        if (newPosts.value.data == null) {
            homeViewModel.getNewPosts()
        }

        if (topPosts.value.data == null) {
            homeViewModel.getTopPosts()
        }

        if (bestPosts.value.data == null) {
            homeViewModel.getBestPosts()
        }

        if (risingPosts.value.data == null) {
            homeViewModel.getRisingsPosts()
        }

        if (controversialPosts.value.data == null) {
            homeViewModel.getControversialPosts()
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
                context.getString(R.string.controversial)
            )
        )
    }

    val pagerState =
        rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { tabs.size }
        )

    Column(modifier = modifier.fillMaxSize().statusBarsPadding()) {
        BRScrollableTabRow(tabs = tabs, pagerState = pagerState)
        BRHorizontalPager(
            pagerState = pagerState,
        ) { page ->
            when (page) {
                0 -> {
                    HomeScreenListings(
                        uiState = hotPosts.value,
                        onClick = onClick,
                        refreshData = { homeViewModel.getHotPosts(true) }
                    )
                }
                1 -> {
                    HomeScreenListings(
                        uiState = newPosts.value,
                        onClick = onClick,
                        refreshData = { homeViewModel.getNewPosts(true) })
                }
                2 -> {
                    HomeScreenListings(
                        uiState = topPosts.value,
                        onClick = onClick,
                        refreshData = { homeViewModel.getTopPosts(true) })
                }
                3 -> {
                    HomeScreenListings(
                        uiState = bestPosts.value,
                        onClick = onClick,
                        refreshData = { homeViewModel.getBestPosts(true) })
                }
                4 -> {
                    HomeScreenListings(
                        uiState = risingPosts.value,
                        onClick = onClick,
                        refreshData = { homeViewModel.getRisingsPosts(true) })
                }
                5 -> {
                    HomeScreenListings(
                        uiState = controversialPosts.value,
                        onClick = onClick,
                        refreshData = { homeViewModel.getControversialPosts(true) })
                }
                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(R.string.unavailable))
                    }
                }
            }
        }
    }
}
