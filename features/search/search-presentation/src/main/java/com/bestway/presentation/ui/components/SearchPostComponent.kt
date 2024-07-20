package com.bestway.presentation.ui.components

import android.webkit.URLUtil
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    var shouldShowPreview by rememberSaveable { mutableStateOf(true) }

    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .weight(2.5f),
                ) {
                    SubRedditName(subName = redditPostUiModel.subName)
                    redditPostUiModel.title?.let { title -> PostTitle(title = title) }
                    redditPostUiModel.description?.let { subtitle ->
                        if (subtitle.isNotEmpty()) {
                            PostDescription(description = subtitle, maxLines = 2)
                        }
                    }
                }
                redditPostUiModel.thumbnailUrl?.let { imageUrl ->
                    if (URLUtil.isValidUrl(imageUrl)) {
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(100.dp)
                                .weight(1f),
                            contentAlignment = Alignment.BottomStart,
                        ) {
                            PostImage(
                                imageUrl = imageUrl,
                                onError = {
                                    shouldShowPreview = false
                                }
                            )

                            if (shouldShowPreview) {
                                if (!redditPostUiModel.videoUrl.isNullOrEmpty() || !redditPostUiModel.gifUrl.isNullOrEmpty()) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
                                                shape = CircleShape,
                                            ),
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = stringResource(
                                            R.string.content_description_post_image
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
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
}