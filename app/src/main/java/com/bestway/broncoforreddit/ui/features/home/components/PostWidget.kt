package com.bestway.broncoforreddit.ui.features.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.bestway.broncoforreddit.R

@Composable
fun PostWidget(
    modifier: Modifier = Modifier,
    subName: String,
    title: String?,
    description: String?,
    upVotes: Int,
    comments: Int,
    imageUrl: String?,
    postUrl: String?,
    videoUrl: String?
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
        title?.let {
            PostTitle(title = title, showFullPost = showFullPost)
        }
        description?.let {
            if (it.isNotEmpty()) {
                PostDescription(description = it, showFullPost = showFullPost)
            }
        }
        imageUrl?.let {
            if (it.endsWith("png") || it.endsWith("jpg")) {
                PostImage(imageUrl = imageUrl)
            }
            if (it.contains(".gif")) {
                PostVideo(videoUrl = imageUrl)
            }
        }
        videoUrl?.let {
            PostVideo(videoUrl = videoUrl)
        }
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
        maxLines = if (!showFullPost) 3 else 100,
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
        maxLines = if (!showFullPost) 3 else 100,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun PostImage(imageUrl: String) {
    val interactionSource = remember { MutableInteractionSource() }
    var isImageLoading by rememberSaveable { mutableStateOf(false) }
    var isImageLoadingError by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (isImageLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(vertical = 64.dp)
            )
        }
        AsyncImage(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    role = Role.Image,
                    indication = null
                ) { }
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            model = imageUrl,
            contentScale = ContentScale.FillBounds,
            onLoading = { isImageLoading = true },
            onSuccess = { isImageLoading = false },
            onError = {
                // TODO: fix this and use a proper error image
                isImageLoading = false
                isImageLoadingError = true
            },
            contentDescription = "This is a reddit post image."
        )
    }
}

@Composable
fun PostVideo(videoUrl: String) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            this.setMediaItem(MediaItem.fromUri(videoUrl))
            this.prepare()
            this.play()
        }
    }

    DisposableEffect(
        AndroidView(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .defaultMinSize(minHeight = 250.dp)
                .fillMaxWidth(),
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
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
        title = "Post Title",
        description = "Post Description",
        upVotes = 7200,
        comments = 4300,
        imageUrl = "https://i.redd.it/cdb74knmavpb1.jpg\n",
        postUrl = "https://sample_post_url",
        videoUrl = "https://sample_video_url"
    )
}
