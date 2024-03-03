package com.bestway.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.bestway.design_system.ui_components.BRLinearProgressIndicator
import com.bestway.design_system.utils.slideInFromBottomTransition
import com.bestway.home_presentation.R
import com.bestway.presentation.model.RedditPostUiModel
import com.bestway.presentation.ui.screens.home.PostsUiState

@Composable
fun HomeScreenListings(
    uiState: PostsUiState,
    onClick: (redditPostUiModel: RedditPostUiModel) -> Unit
) {
    val list by remember(uiState) { mutableStateOf(uiState.data.orEmpty()) }

    AnimatedVisibility(visible = list.isNotEmpty(), enter = slideInFromBottomTransition()) {
        LazyColumn {
            itemsIndexed(
                items = list,
                key = { _, item ->
                    item.id
                },
                contentType = { _, _ ->
                    "reddit_post"
                }
            ) {index, item ->
                PostComponent(
                    modifier =
                    when (index) {
                        list.size - 1 -> Modifier.navigationBarsPadding()
                        else -> Modifier
                    },
                    redditPostUiModel = item,
                    onClick = onClick
                )
            }
        }
    }

    // Show error screen
    AnimatedVisibility(
        visible = !uiState.errorMessage.isNullOrBlank(),
        enter = slideInFromBottomTransition()
    ) {
        var showLogs by rememberSaveable { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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
