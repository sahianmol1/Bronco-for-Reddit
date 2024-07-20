package com.bestway.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.anmolsahi.common_ui.components.PostActionItem
import com.anmolsahi.common_ui.components.PostDescription
import com.anmolsahi.common_ui.components.PostImage
import com.anmolsahi.common_ui.components.PostTitle
import com.anmolsahi.common_ui.components.SubRedditName
import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.bestway.search_presentation.R

@Composable
fun SearchPostComponent(
    redditPostUiModel: RedditPostUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row {
            Column {
                SubRedditName(subName = redditPostUiModel.subName)
                redditPostUiModel.title?.let { title -> PostTitle(title = title) }
                redditPostUiModel.description?.let { subtitle ->
                    if (subtitle.isNotEmpty()) {
                        PostDescription(description = subtitle, maxLines = 2)
                    }
                }
            }
            redditPostUiModel.imageUrl?.let { imageUrl -> PostImage(imageUrl = imageUrl) }
        }

        Row {
            PostActionItem(
                icon = Icons.Default.ArrowUpward,
                label = redditPostUiModel.upVotes.toString(),
                actionDescription = stringResource(R.string.upvote),
            )
            PostActionItem(
                icon = Icons.AutoMirrored.Outlined.Message,
                label = redditPostUiModel.comments.toString(),
                actionDescription = stringResource(R.string.comment),
            )
        }
    }
}