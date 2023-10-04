package com.bestway.broncoforreddit.ui.features.postdetails.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bestway.broncoforreddit.ui.features.home.components.PostActions
import com.bestway.broncoforreddit.ui.features.home.components.SubRedditName
import com.bestway.broncoforreddit.ui.models.RedditPostUiModel

@Composable
fun PostDetailsScreen(
    modifier: Modifier = Modifier,
    redditPostUiModel: RedditPostUiModel
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp),
    ) {
        SubRedditName(subName = redditPostUiModel.subName)
        PostDetailsTitle(title = redditPostUiModel.title.orEmpty())
        PostDetailsDescription(description = redditPostUiModel.description.orEmpty())
        PostActions(upVotes = redditPostUiModel.upVotes, comments = redditPostUiModel.comments)
    }
}

@Composable
fun PostDetailsTitle(title: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        fontWeight = FontWeight.Bold,
        text = title
    )
}

@Composable
fun PostDetailsDescription(description: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        text = description,
        color = MaterialTheme.colorScheme.onSecondaryContainer
    )
}