package com.bestway.broncoforreddit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bestway.broncoforreddit.components.PostWidget
import com.bestway.broncoforreddit.data.mock.listOfMockedPosts
import com.bestway.broncoforreddit.ui.theme.BroncoForRedditTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()
            val list by remember { mutableStateOf(listOfMockedPosts) }
            val listSize by rememberSaveable { mutableStateOf(list.size) }
            val lastElementOfList by rememberSaveable { mutableStateOf(list.size - 1) }

            val tabs by rememberSaveable {
                mutableStateOf(
                    listOf(
                        "Hot",
                        "Best",
                        "New",
                        "Rising",
                        "Controversial",
                        "Top"
                    )
                )
            }

            BroncoForRedditTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                    ) {
                        ScrollableTabRow(
                            selectedTabIndex = pagerState.currentPage
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(text = title) },
                                    selected = pagerState.currentPage == index,
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    }
                                )
                            }
                        }
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
                }
            }
        }
    }
}
