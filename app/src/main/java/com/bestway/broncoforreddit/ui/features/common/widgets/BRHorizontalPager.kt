package com.bestway.broncoforreddit.ui.features.common.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bestway.broncoforreddit.data.repositories.models.ListingsChildren
import com.bestway.broncoforreddit.ui.features.redditlistings.components.PostWidget

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BRHorizontalPager(tabs: List<String>, pagerState: PagerState, list: List<ListingsChildren>) {

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        pageCount = tabs.size,
        state = pagerState
    ) { page ->
        when (page) {
            0, 1, 2, 5 -> {
                AnimatedVisibility(
                    visible = list.isNotEmpty(),
                    enter = slideInVertically()
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
                    ){
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