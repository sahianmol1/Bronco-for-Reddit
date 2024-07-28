package com.anmolsahi.postdetailspresentation.postdetails.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.commonui.utils.orZero
import com.anmolsahi.postdetailspresentation.R
import com.anmolsahi.postdetailspresentation.postdetails.components.CommentsComponentDefaults.DEFAULT_MAX_LINES

object CommentsComponentDefaults {
    const val DEFAULT_MAX_LINES = 3
}

@Composable
fun CommentsComponent(
    commentDetails: RedditPostUiModel,
    originalPosterName: String,
    modifier: Modifier = Modifier,
) {
    var maxLines by rememberSaveable { mutableIntStateOf(DEFAULT_MAX_LINES) }
    var showReplies by rememberSaveable { mutableStateOf(false) }
    val commentsCount by rememberSaveable {
        mutableIntStateOf(
            commentDetails.replies?.filter {
                it.author.isNotEmpty() && !it.author.contains("mod", true)
            }?.size.orZero(),
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                maxLines = if (maxLines == DEFAULT_MAX_LINES) {
                    Int.MAX_VALUE
                } else {
                    DEFAULT_MAX_LINES
                }
            }
            .padding(top = 12.dp, start = 16.dp, bottom = 4.dp)
            .animateContentSize(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            UserImage()
            if (originalPosterName == commentDetails.author) OPBadge()
            UserName(commentDetails.author)
        }
        CommentText(text = commentDetails.body.orEmpty(), maxLines = maxLines)
        Row(verticalAlignment = Alignment.CenterVertically) {
            CommentUpVotes(
                modifier = Modifier.padding(
                    vertical = if (commentDetails.replies?.isEmpty() == true) 12.dp else 0.dp,
                ),
                count = commentDetails.upVotes,
            )
            if (commentsCount != 0) {
                CommentReplies(
                    modifier = Modifier.padding(start = 16.dp),
                    count = commentsCount,
                )
                ViewReplies(
                    title = if (!showReplies) {
                        stringResource(R.string.view_replies)
                    } else {
                        stringResource(R.string.hide_replies)
                    },
                    onClick = {
                        showReplies = !showReplies
                    },
                )
            }
        }
        if (showReplies) {
            commentDetails.replies?.forEach { reply ->
                if (reply.author.isNotEmpty()) {
                    CommentsComponent(
                        commentDetails = reply,
                        originalPosterName = originalPosterName,
                    )
                }
            }
        }
    }
}

@Composable
fun UserImage() {
    Icon(
        imageVector = Icons.Rounded.Person,
        contentDescription = "This is the comment poster's avatar.",
    )
}

@Composable
fun OPBadge() {
    val opBackgroundColor = MaterialTheme.colorScheme.primary
    Text(
        modifier = Modifier
            .clickable {}
            .padding(start = 4.dp, end = 4.dp)
            .drawBehind { drawCircle(color = opBackgroundColor) }
            .padding(4.dp),
        text = "OP",
        fontSize = 8.sp,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

@Composable
fun UserName(authorName: String) {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        text = "u/$authorName",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun CommentText(text: String, maxLines: Int) {
    Text(
        modifier = Modifier
            .padding(top = 8.dp)
            .animateContentSize(),
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.90f),
    )
}

@Composable
fun CommentUpVotes(count: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = stringResource(
                R.string.post_action_content_description,
                "UpVotes",
            ),
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "$count",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CommentReplies(count: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Comment,
            contentDescription = stringResource(
                R.string.post_action_content_description,
                "replies",
            ),
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "$count",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ViewReplies(title: String = stringResource(R.string.view_replies), onClick: () -> Unit = {}) {
    Text(
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp),
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}
