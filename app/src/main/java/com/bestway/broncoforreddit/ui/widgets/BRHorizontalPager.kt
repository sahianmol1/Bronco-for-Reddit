package com.bestway.broncoforreddit.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bestway.broncoforreddit.components.PostWidget
import com.bestway.broncoforreddit.data.mock.MockPost

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BRHorizontalPager(tabs: List<String>, pagerState: PagerState, list: List<MockPost>) {

    val listSize by rememberSaveable { mutableStateOf(list.size) }
    val lastElementOfList by rememberSaveable { mutableStateOf(list.size - 1) }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        pageCount = tabs.size,
        state = pagerState
    ) { page ->
        when (page) {
            0, 1, 2, 5 -> {
                LazyColumn {
                    items(
                        count = listSize,
                        key = { index -> list[index].id }
                    ) { index ->
                        PostWidget(
                            modifier = when (index) {
                                lastElementOfList -> Modifier.navigationBarsPadding()
                                else -> Modifier
                            }
                        )
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
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