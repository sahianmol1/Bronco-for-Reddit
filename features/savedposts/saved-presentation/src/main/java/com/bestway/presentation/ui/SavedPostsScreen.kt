package com.bestway.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.common_ui.components.PostComponent
import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.bestway.design_system.ui_components.BRLinearProgressIndicator
import com.bestway.subreddit_presentation.R

@Composable
fun SavedPostsScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedPostsViewModel = hiltViewModel(),
    onClick: (String) -> Unit = {},
    onSaveIconClick: (String) -> Unit = {},
) {

    val uiState by
        viewModel.savedPostsUiState.collectAsStateWithLifecycle(
            lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
        )
    var list by remember { mutableStateOf(emptyList<RedditPostUiModel>()) }

    LaunchedEffect(uiState.data) {
        viewModel.getAllSavedPosts()
        list = uiState.data.orEmpty()
    }

    if (list.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                items = list,
                key = { _, item -> item.id },
                contentType = { _, _ -> "reddit_post" }
            ) { index, item ->
                PostComponent(
                    modifier =
                        when (index) {
                            0 -> Modifier.statusBarsPadding()
                            list.size - 1 -> Modifier.navigationBarsPadding()
                            else -> Modifier
                        },
                    redditPostUiModel = item,
                    onClick = onClick,
                    onSaveIconClick = onSaveIconClick
                )
            }
        }
    }

    // Show error screen
    if (!uiState.errorMessage.isNullOrBlank() && list.isEmpty()) {
        var showLogs by rememberSaveable { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: Code Cleanup
            Text(
                modifier = Modifier.clickable { showLogs = !showLogs },
                text =
                    if (!showLogs)
                        stringResource(R.string.uh_oh_something_went_wrong) + " Learn More"
                    else
                        stringResource(R.string.uh_oh_something_went_wrong) +
                            " Learn More /n ${uiState.errorMessage}",
                textDecoration = TextDecoration.Underline
            )
        }
    }

    if (uiState.isLoading) {
        BRLinearProgressIndicator()
    }
}
