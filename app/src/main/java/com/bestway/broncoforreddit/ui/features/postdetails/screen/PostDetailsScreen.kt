package com.bestway.broncoforreddit.ui.features.postdetails.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bestway.broncoforreddit.ui.features.home.components.PostActions
import com.bestway.broncoforreddit.ui.features.home.components.PostImage
import com.bestway.broncoforreddit.ui.features.home.components.PostVideo
import com.bestway.broncoforreddit.ui.features.home.components.SubRedditName
import com.bestway.broncoforreddit.ui.models.RedditPostUiModel

@Composable
fun PostDetailsScreen(modifier: Modifier = Modifier, redditPostUiModel: RedditPostUiModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState).fillMaxSize().padding(horizontal = 16.dp),
    ) {
        SubRedditName(subName = redditPostUiModel.subName)
        PostDetailsTitle(title = redditPostUiModel.title.orEmpty())
        PostDetailsDescription(description = redditPostUiModel.description.orEmpty())
        redditPostUiModel.imageUrl?.let {
            if (it.endsWith("png") || it.endsWith("jpg")) {
                PostImage(imageUrl = redditPostUiModel.imageUrl)
            }
            if (it.contains(".gif")) {
                redditPostUiModel.gifUrl?.let {
                    PostVideo(videoUrl = redditPostUiModel.gifUrl)
                }
            }
        }
        redditPostUiModel.videoUrl?.let {
            PostVideo(videoUrl = it)
        }
        PostActions(
            modifier = Modifier.padding(top = 8.dp),
            upVotes = redditPostUiModel.upVotes,
            comments = redditPostUiModel.comments
        )
    }
}

@Composable
fun PostDetailsTitle(title: String) {
    Text(modifier = Modifier.padding(vertical = 4.dp), fontWeight = FontWeight.Bold, text = title)
}

@Composable
fun PostDetailsDescription(description: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        text = description,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.90f)
    )
}
