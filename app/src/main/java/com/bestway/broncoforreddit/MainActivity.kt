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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bestway.broncoforreddit.components.PostWidget
import com.bestway.broncoforreddit.data.mock.listOfMockedPosts
import com.bestway.broncoforreddit.ui.model.BottomNav
import com.bestway.broncoforreddit.ui.theme.BroncoForRedditTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
                        "New",
                        "Top",
                        "Best",
                        "Rising",
                        "Controversial"
                    )
                )
            }

            val bottomNavs by remember {
                mutableStateOf(
                    listOf(
                        BottomNav(
                            title = "Home",
                            selectedIcon = Icons.Filled.Home,
                            unselectedIcon = Icons.Outlined.Home
                        ),
                        BottomNav(
                            title = "Search",
                            selectedIcon = Icons.Filled.Search,
                            unselectedIcon = Icons.Outlined.Search
                        ),
                        BottomNav(
                            title = "Create",
                            selectedIcon = Icons.Filled.Add,
                            unselectedIcon = Icons.Outlined.Add
                        ),
                        BottomNav(
                            title = "Subs",
                            selectedIcon = Icons.Filled.List,
                            unselectedIcon = Icons.Outlined.List
                        ),
                        BottomNav(
                            title = "About",
                            selectedIcon = Icons.Filled.Info,
                            unselectedIcon = Icons.Outlined.Info
                        ),
                    )
                )
            }

            var selectedBottomNavIndex by rememberSaveable { mutableStateOf(0) }

            BroncoForRedditTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                bottomNavs.forEachIndexed { index, bottomNav ->
                                    NavigationBarItem(
                                        selected = selectedBottomNavIndex == index,
                                        onClick = {
                                            selectedBottomNavIndex = index
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (selectedBottomNavIndex == index) {
                                                    bottomNav.selectedIcon
                                                } else {
                                                    bottomNav.unselectedIcon
                                                },
                                                contentDescription = "Bottom Bar: ${bottomNav.title}"
                                            )

                                        },
                                        label = {
                                            Text(text = bottomNav.title)
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding()
                                .padding(it)
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
}
