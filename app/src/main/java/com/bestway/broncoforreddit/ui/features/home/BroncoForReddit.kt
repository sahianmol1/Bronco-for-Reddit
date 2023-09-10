package com.bestway.broncoforreddit.ui.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bestway.broncoforreddit.R
import com.bestway.broncoforreddit.data.mock.listOfMockedPosts
import com.bestway.broncoforreddit.ui.features.common.widgets.BRHorizontalPager
import com.bestway.broncoforreddit.ui.features.common.widgets.BRNavigationBar
import com.bestway.broncoforreddit.ui.features.common.widgets.BRScrollableTabRow

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BroncoForReddit() {
    val context = LocalContext.current

    val pagerState = rememberPagerState()
    val list by remember { mutableStateOf(listOfMockedPosts) }

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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = { BRNavigationBar(context = context) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(it)
            ) {
                BRScrollableTabRow(
                    tabs = tabs,
                    pagerState = pagerState
                )
                BRHorizontalPager(
                    tabs = tabs,
                    pagerState = pagerState,
                    list = list
                )
            }
        }
    }
}