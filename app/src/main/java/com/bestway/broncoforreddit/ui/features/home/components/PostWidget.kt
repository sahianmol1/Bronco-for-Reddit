package com.bestway.broncoforreddit.ui.features.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bestway.broncoforreddit.R

@Composable
fun PostWidget(
    modifier: Modifier = Modifier,
    subName: String,
    title: String,
    description: String,
    upVotes: Int,
    comments: Int
) {
    var showFullPost by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .animateContentSize()
            .clickable {
                showFullPost = !showFullPost
            }
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp),
    ) {
        SubRedditName(subName = subName)
        PostTitle(title = title, showFullPost = showFullPost)
        PostDescription(description = description, showFullPost = showFullPost)
        PostActions(upVotes = upVotes, comments = comments)
        Divider(
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}

@Composable
fun SubRedditName(
    subName: String
) {
    Text(
        modifier = Modifier
            .clickable { }
            .padding(vertical = 8.dp),
        style = TextStyle(
            fontWeight = FontWeight.Bold
        ),
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        text = subName,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PostTitle(title: String, showFullPost: Boolean) {
    Text(
        modifier = Modifier
            .padding(vertical = 4.dp),
        fontWeight = FontWeight.Bold,
        text = title,
        maxLines = if(!showFullPost) 3 else 100,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PostDescription(description: String, showFullPost: Boolean) {
    Text(
        modifier = Modifier
            .padding(vertical = 4.dp),
        text = description,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.90f),
        maxLines = if(!showFullPost) 3 else 100,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PostActions(upVotes: Int, comments: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PostActionItem(
            icon = Icons.Default.ArrowUpward,
            label = upVotes.toString(),
            actionDescription = stringResource(R.string.upvote)
        )
        PostActionItem(
            icon = Icons.Default.Message,
            label = comments.toString(),
            actionDescription = stringResource(R.string.comment)
        )
        PostActionItem(
            icon = Icons.Default.Share,
            label = stringResource(R.string.share),
            actionDescription = stringResource(R.string.share)
        )
    }
}

@Composable
fun PostActionItem(icon: ImageVector, label: String, actionDescription: String) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 8.dp)
                .padding(vertical = 8.dp),
            imageVector = icon,
            contentDescription = stringResource(
                R.string.post_action_content_description,
                actionDescription
            )
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp),
            text = label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PostPreview() {
    PostWidget(
        subName = "r/onexindia",
        upVotes = 7200,
        comments = 4300,
        description = "I always worry that my partner is not smart enough and keep rejecting people based on this, so much that I have developed a bias that women should have good money understanding. How should I stop caring about this. Please help.",
        title = "How rich do I have to be to stop caring if my partner is smart in money matters?"
    )
}
