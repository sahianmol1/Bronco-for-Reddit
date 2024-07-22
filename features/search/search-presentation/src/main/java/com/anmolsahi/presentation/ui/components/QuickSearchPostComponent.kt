package com.anmolsahi.presentation.ui.components

import android.webkit.URLUtil
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anmolsahi.commonui.components.PostDescription
import com.anmolsahi.commonui.components.PostImage
import com.anmolsahi.commonui.components.PostTitle
import com.anmolsahi.commonui.components.SubRedditName
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.searchpresentation.R

@Composable
fun QuickSearchPostComponent(
    redditPostUiModel: RedditPostUiModel,
    modifier: Modifier = Modifier,
    onPostClick: (postId: String, postUrl: String) -> Unit,
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onPostClick(redditPostUiModel.id, redditPostUiModel.postUrl.orEmpty()) }
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
                            PostImage(imageUrl = imageUrl)

                            if (!redditPostUiModel.videoUrl.isNullOrEmpty() || !redditPostUiModel.gifUrl.isNullOrEmpty()) {
                                Icon(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.background.copy(
                                                alpha = 0.7f
                                            ),
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

            Row {
                SearchPostActionItem(
                    icon = Icons.Default.ArrowUpward,
                    label = redditPostUiModel.upVotes.toString(),
                    actionDescription = stringResource(R.string.upvote),
                )
                SearchPostActionItem(
                    modifier = Modifier.padding(start = 8.dp),
                    icon = Icons.AutoMirrored.Outlined.Message,
                    label = redditPostUiModel.comments.toString(),
                    actionDescription = stringResource(R.string.comment),
                )
            }
        }
    }
}

@Composable
fun SearchPostActionItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    actionDescription: String,
) {
    Row(
        modifier = modifier
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier,
            imageVector = icon,
            contentDescription =
            stringResource(
                com.anmolsahi.commonui.R.string.post_action_content_description,
                actionDescription
            ),
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            text = label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
        )
    }
}