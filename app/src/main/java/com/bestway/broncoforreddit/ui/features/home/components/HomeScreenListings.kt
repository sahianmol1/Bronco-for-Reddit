package com.bestway.broncoforreddit.ui.features.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.bestway.broncoforreddit.R
import com.bestway.broncoforreddit.ui.components.BRLinearProgressIndicator
import com.bestway.broncoforreddit.ui.features.home.PostsUiState
import com.bestway.broncoforreddit.ui.models.RedditPostUiModel
import com.bestway.broncoforreddit.utils.slideInFromBottomTransition

@Composable
fun HomeScreenListings(
    uiState: PostsUiState,
    onClick: (redditPostUiModel: RedditPostUiModel) -> Unit
) {
    val list by remember(uiState) { mutableStateOf(uiState.data?.children.orEmpty()) }

    AnimatedVisibility(visible = list.isNotEmpty(), enter = slideInFromBottomTransition()) {
        LazyColumn {
            items(count = list.size, key = { index -> list[index].childrenData.id }) { index ->
                PostComponent(
                    modifier =
                        when (index) {
                            list.size - 1 -> Modifier.navigationBarsPadding()
                            else -> Modifier
                        },
                    redditPostUiModel =
                        RedditPostUiModel(
                            subName = list[index].childrenData.subName.orEmpty(),
                            title = list[index].childrenData.title,
                            description = list[index].childrenData.description,
                            imageUrl = list[index].childrenData.imageUrl,
                            postUrl = list[index].childrenData.postUrl,
                            upVotes = list[index].childrenData.upVotes ?: 0,
                            comments = list[index].childrenData.comments ?: 0,
                            videoUrl = list[index].childrenData.secureMedia?.redditVideo?.videoUrl,
                            gifUrl = list[index].childrenData.gifUrl?.gifPreview?.url,
                            author = list[index].childrenData.author.orEmpty()
                        ),
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
