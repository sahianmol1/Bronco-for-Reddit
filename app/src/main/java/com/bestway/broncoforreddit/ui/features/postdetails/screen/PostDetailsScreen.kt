package com.bestway.broncoforreddit.ui.features.postdetails.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bestway.broncoforreddit.ui.features.comments.CommentsView
import com.bestway.broncoforreddit.ui.features.home.components.OriginalPosterName
import com.bestway.broncoforreddit.ui.features.home.components.PostActions
import com.bestway.broncoforreddit.ui.features.home.components.PostImage
import com.bestway.broncoforreddit.ui.features.home.components.PostVideo
import com.bestway.broncoforreddit.ui.features.home.components.SubRedditName
import com.bestway.broncoforreddit.ui.models.RedditPostUiModel

@Composable
fun PostDetailsScreen(modifier: Modifier = Modifier, redditPostUiModel: RedditPostUiModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier =
            modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        SubRedditName(subName = redditPostUiModel.subName)

        OriginalPosterName(opName = redditPostUiModel.author)

        if (!redditPostUiModel.title.isNullOrBlank()) {
            PostDetailsTitle(title = redditPostUiModel.title)
        }

        if (!redditPostUiModel.description.isNullOrBlank()) {
            PostDetailsDescription(description = redditPostUiModel.description)
        }

        redditPostUiModel.imageUrl?.let {
            if (it.endsWith("png") || it.endsWith("jpg")) {
                PostImage(imageUrl = redditPostUiModel.imageUrl)
            }
            if (it.contains(".gif")) {
                redditPostUiModel.gifUrl?.let { PostVideo(videoUrl = redditPostUiModel.gifUrl) }
            }
        }

        redditPostUiModel.videoUrl?.let { videoUrl -> PostVideo(videoUrl = videoUrl) }

        PostActions(
            modifier = Modifier.padding(top = 8.dp),
            upVotes = redditPostUiModel.upVotes,
            comments = redditPostUiModel.comments
        )

        Divider()

        for (i in 0..20) {
            CommentsView()
        }
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
