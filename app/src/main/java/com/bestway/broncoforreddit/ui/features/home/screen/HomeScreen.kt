package com.bestway.broncoforreddit.ui.features.home.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bestway.broncoforreddit.R
import com.bestway.broncoforreddit.data.models.ListingsChildren
import com.bestway.broncoforreddit.ui.components.BRHorizontalPager
import com.bestway.broncoforreddit.ui.components.BRScrollableTabRow
import com.bestway.broncoforreddit.ui.features.home.HomeViewModel
import com.bestway.broncoforreddit.ui.features.home.components.HomeScreenListings

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val trendingPosts = homeViewModel.trendingPosts.collectAsStateWithLifecycle()
    val newPosts = homeViewModel.newPosts.collectAsStateWithLifecycle()
    val topPosts = homeViewModel.topPosts.collectAsStateWithLifecycle()
    val bestPosts = homeViewModel.bestPosts.collectAsStateWithLifecycle()
    val risingPosts = homeViewModel.risingPosts.collectAsStateWithLifecycle()
    val controversialPosts = homeViewModel.controversialPosts.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.getTrendingPosts()
        homeViewModel.getNewPosts()
        homeViewModel.getTopPosts()
        homeViewModel.getBestPosts()
        homeViewModel.getRisingsPosts()
        homeViewModel.getControversialPosts()
    }

    val tabs by rememberSaveable {
        mutableStateOf(
            listOf(
                context.getString(R.string.trending),
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

    var trendingList by remember { mutableStateOf(listOf<ListingsChildren>()) }

    LaunchedEffect(trendingPosts.value.children) {
        trendingList = trendingPosts.value.children.orEmpty()
    }

    Column(modifier = modifier.fillMaxSize()) {
        BRScrollableTabRow(tabs = tabs, pagerState = pagerState)
        BRHorizontalPager(
            pagerState = pagerState,
        ) { page ->
            when (page) {
                0 -> {
                    HomeScreenListings(list = trendingList)
                }
                1 -> {
                    HomeScreenListings(list = newPosts.value.children.orEmpty())
                }
                2 -> {
                    HomeScreenListings(list = topPosts.value.children.orEmpty())
                }
                3 -> {
                    HomeScreenListings(list = bestPosts.value.children.orEmpty())
                }
                4 -> {
                    HomeScreenListings(list = risingPosts.value.children.orEmpty())
                }
                5 -> {
                    HomeScreenListings(list = controversialPosts.value.children.orEmpty())
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
