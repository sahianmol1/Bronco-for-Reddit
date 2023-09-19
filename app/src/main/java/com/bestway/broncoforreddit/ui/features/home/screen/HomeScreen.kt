package com.bestway.broncoforreddit.ui.features.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bestway.broncoforreddit.R
import com.bestway.broncoforreddit.data.repositories.models.ListingsChildren
import com.bestway.broncoforreddit.ui.features.common.widgets.BRHorizontalPager
import com.bestway.broncoforreddit.ui.features.common.widgets.BRNavigationBar
import com.bestway.broncoforreddit.ui.features.common.widgets.BRScrollableTabRow
import com.bestway.broncoforreddit.ui.features.home.HomeViewModel
import com.bestway.broncoforreddit.ui.features.redditlistings.components.PostWidget

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current

    val pagerState = rememberPagerState()
    val trendingPosts = homeViewModel.trendingPosts.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.getTrendingPosts()
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

    var list by remember { mutableStateOf(listOf<ListingsChildren>()) }

    LaunchedEffect(trendingPosts.value.children) {
        list = trendingPosts.value.children.orEmpty()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = { BRNavigationBar(context = context) }
        ) { scaffoldPaddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPaddingValues)
            ) {
                BRScrollableTabRow(
                    tabs = tabs,
                    pagerState = pagerState
                )
                BRHorizontalPager(
                    tabs = tabs,
                    pagerState = pagerState,
                ) { page ->
                    when (page) {
                        0, 1, 2, 5 -> {
                            AnimatedVisibility(
                                visible = list.isNotEmpty(),
                                enter = slideInVertically(
                                    initialOffsetY = { screenHeight ->
                                        screenHeight / 2
                                    }
                                )
                            ) {
                                LazyColumn {
                                    items(
                                        count = list.size,
                                        key = { index -> list[index].childrenData.id }
                                    ) { index ->
                                        PostWidget(
                                            modifier = when (index) {
                                                list.size - 1 -> Modifier.navigationBarsPadding()
                                                else -> Modifier
                                            },
                                            subName = list[index].childrenData.subName.orEmpty(),
                                            title = list[index].childrenData.title.orEmpty(),
                                            description = list[index].childrenData.description.orEmpty(),
                                            upVotes = list[index].childrenData.upVotes ?: 0,
                                            comments = list[index].childrenData.comments ?: 0,
                                        )
                                    }
                                }
                            }
                            if (list.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    LinearProgressIndicator()
                                }
                            }
                        }

                        else -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Hello World"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}