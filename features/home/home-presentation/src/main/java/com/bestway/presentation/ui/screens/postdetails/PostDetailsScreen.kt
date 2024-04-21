package com.bestway.presentation.ui.screens.postdetails

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
import com.bestway.design_system.models.RedditPostUiModel
import com.bestway.design_system.ui_components.post.OriginalPosterName
import com.bestway.design_system.ui_components.post.PostActions
import com.bestway.design_system.ui_components.post.PostImage
import com.bestway.design_system.ui_components.post.PostVideo
import com.bestway.design_system.ui_components.post.SubRedditName
import com.bestway.presentation.ui.components.CommentsView

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
            PostDetailsTitle(title = redditPostUiModel.title.orEmpty())
        }

        if (!redditPostUiModel.description.isNullOrBlank()) {
            PostDetailsDescription(description = redditPostUiModel.description.orEmpty())
        }

        redditPostUiModel.imageUrl?.let {
            if (it.endsWith("png") || it.endsWith("jpg")) {
                PostImage(imageUrl = redditPostUiModel.imageUrl.orEmpty())
            }
            if (it.contains(".gif")) {
                redditPostUiModel.gifUrl?.let {
                    PostVideo(
                        videoUrl = redditPostUiModel.gifUrl.orEmpty()
                    )
                }
            }
        }

        redditPostUiModel.videoUrl?.let { videoUrl ->
            PostVideo(
                videoUrl = videoUrl
            )
        }

        PostActions(
            modifier = Modifier.padding(top = 8.dp),
            upVotes = redditPostUiModel.upVotes,
            comments = redditPostUiModel.comments,
            isSaved = false
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
