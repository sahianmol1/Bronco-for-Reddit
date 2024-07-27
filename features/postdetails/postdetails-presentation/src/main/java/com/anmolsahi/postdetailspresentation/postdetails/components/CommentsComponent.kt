package com.anmolsahi.postdetailspresentation.postdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CommentsComponent(commentDetails: RedditPostUiModel, modifier: Modifier = Modifier) {
    if (!commentDetails.author.contains("mod", true)) {
        Column(modifier = modifier.padding(top = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                UserImage()
                OPBadge()
                UserName(commentDetails.author)
            }
            CommentText(commentDetails.body.orEmpty())
            Row(verticalAlignment = Alignment.CenterVertically) {
                CommentUpVotes(count = commentDetails.upVotes)
                if (commentDetails.replies?.isNotEmpty() == true) {
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
            .padding(start = 4.dp, end = 8.dp)
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
        modifier = Modifier.clickable {},
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        text = "u/$authorName",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun CommentText(text: String) {
    Text(modifier = Modifier.padding(top = 8.dp), text = text)
}

@Composable
fun CommentUpVotes(count: Int) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable {}
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.padding(vertical = 8.dp),
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = stringResource(
                R.string.post_action_content_description,
                "UpVotes",
            ),
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
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
            .padding(start = 16.dp, bottom = 8.dp)
            .clickable {},
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        text = "View Replies",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}
