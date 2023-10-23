package com.bestway.broncoforreddit.ui.features.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bestway.broncoforreddit.ui.components.BRLinearProgressIndicator
import com.bestway.broncoforreddit.ui.features.home.PostsUiState
import com.bestway.broncoforreddit.ui.models.RedditPostUiModel
import com.bestway.broncoforreddit.utils.slideInFromBottomTransition

@Composable
fun HomeScreenListings(uiState: PostsUiState) {
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
                            gifUrl = list[index].childrenData.gifUrl?.gifPreview?.url
                        )
                )
            }
        }
    }

    AnimatedVisibility(
        visible = !uiState.errorMessage.isNullOrBlank(),
        enter = slideInFromBottomTransition()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Uh oh! Something went wrong.")
        }
    }

    if (uiState.isLoading) {
        BRLinearProgressIndicator()
    }
}
