package com.anmolsahi.postdetailspresentation.postdetails.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.anmolsahi.commonui.R
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.commonui.utils.orZero
import com.anmolsahi.postdetailspresentation.postdetails.components.CommentsComponentDefaults.DEFAULT_MAX_LINES

object CommentsComponentDefaults {
    const val DEFAULT_MAX_LINES = 3
}

@Composable
fun CommentsComponent(
    commentDetails: RedditPostUiModel,
    isOriginalPoster: Boolean,
    modifier: Modifier = Modifier,
) {
    var maxLines by rememberSaveable { mutableIntStateOf(DEFAULT_MAX_LINES) }
    if (!commentDetails.author.contains("mod", true)) {
        Column(
            modifier = modifier
                .clickable {
                    maxLines = if (maxLines == DEFAULT_MAX_LINES) {
                        Int.MAX_VALUE
                    } else {
                        DEFAULT_MAX_LINES
                    }
                }
                .padding(top = 12.dp, start = 16.dp, end = 16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                UserImage()
                if (isOriginalPoster) OPBadge()
                UserName(commentDetails.author)
            }
            CommentText(text = commentDetails.body.orEmpty(), maxLines = maxLines)
            Row(verticalAlignment = Alignment.CenterVertically) {
                CommentUpVotes(count = commentDetails.upVotes)
                if (commentDetails.replies?.isNotEmpty() == true) {
                    CommentReplies(
                        modifier = Modifier.padding(start = 16.dp),
                        count = commentDetails.replies?.size.orZero(),
                    )
                    ViewReplies()
                }
            }
            HorizontalDivider()
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
        modifier = modifier.wrapContentHeight(),
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
fun ViewReplies() {
    Text(
        modifier = Modifier
            .clickable {}
            .padding(16.dp),
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        text = stringResource(com.anmolsahi.postdetailspresentation.R.string.view_replies),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}
