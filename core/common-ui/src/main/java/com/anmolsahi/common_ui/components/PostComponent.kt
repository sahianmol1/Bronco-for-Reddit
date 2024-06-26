package com.anmolsahi.common_ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
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
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.anmolsahi.common_ui.R
import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.anmolsahi.common_ui.utils.rememberLifecycleEvent

@Composable
fun PostComponent(
    modifier: Modifier = Modifier,
    redditPostUiModel: RedditPostUiModel,
    shouldShowDeleteIcon: Boolean = false,
    onClick: (postId: String) -> Unit,
    onSaveIconClick: (postId: String) -> Unit,
    onDeleteIconClick: (postId: String) -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .animateContentSize()
                .clickable { onClick(redditPostUiModel.id) }
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
    ) {
        SubRedditName(subName = redditPostUiModel.subName)
        OriginalPosterName(opName = redditPostUiModel.author)
        redditPostUiModel.title?.let { PostTitle(title = redditPostUiModel.title) }
        redditPostUiModel.description?.let {
            if (it.isNotEmpty()) {
                PostDescription(description = it)
            }
        }
        redditPostUiModel.imageUrl?.let {
            if (it.endsWith("png") || it.endsWith("jpg")) {
                PostImage(imageUrl = redditPostUiModel.imageUrl)
            }
            if (it.contains(".gif")) {
                redditPostUiModel.gifUrl?.let { PostVideo(videoUrl = redditPostUiModel.gifUrl) }
            }
        }
        redditPostUiModel.videoUrl?.let { PostVideo(videoUrl = it) }
        PostActions(
            upVotes = redditPostUiModel.upVotes,
            comments = redditPostUiModel.comments,
            isSaved = redditPostUiModel.isSaved,
            shouldShowDeleteIcon = shouldShowDeleteIcon,
            onSaveIconClick = { onSaveIconClick(redditPostUiModel.id) },
            onDeleteIconClick = { onDeleteIconClick(redditPostUiModel.id) },
        )
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun SubRedditName(subName: String) {
    Text(
        modifier =
            Modifier
                .clickable {}
                .padding(top = 8.dp, bottom = 4.dp),
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        text = subName,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun OriginalPosterName(opName: String) {
    Text(
        modifier =
            Modifier
                .clickable {}
                .padding(vertical = 4.dp),
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        text = "u/$opName",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun PostTitle(title: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        fontWeight = FontWeight.Bold,
        text = title,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun PostDescription(description: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        text = description,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.90f),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun PostImage(imageUrl: String) {
    val interactionSource = remember { MutableInteractionSource() }
    var isImageLoading by rememberSaveable { mutableStateOf(false) }
    var isImageLoadingError by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        if (isImageLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 64.dp))
        }
        AsyncImage(
            modifier =
                Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        role = Role.Image,
                        indication = null,
                    ) {}
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
            model = imageUrl,
            contentScale = ContentScale.FillWidth,
            onLoading = { isImageLoading = true },
            onSuccess = { isImageLoading = false },
            onError = {
                // Fix this and use a proper error image
                isImageLoading = false
                isImageLoadingError = true
            },
            contentDescription = "This is a reddit post image.",
        )
    }
}

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun PostVideo(videoUrl: String) {
    val context = LocalContext.current

    val lifecycleEvent = rememberLifecycleEvent()

    var isVolumeOff by rememberSaveable { mutableStateOf(true) }

    val exoPlayer =
        remember {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(videoUrl))
                repeatMode = Player.REPEAT_MODE_ALL
                prepare()
                volume = 0f
                play()
            }
        }

    Box(contentAlignment = Alignment.BottomEnd) {
        DisposableEffect(videoUrl) { onDispose { exoPlayer.release() } }

        AndroidView(
            modifier =
                Modifier
                    .clickable {}
                    .padding(vertical = 4.dp)
                    .defaultMinSize(minHeight = 250.dp)
                    .fillMaxWidth(),
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = false
                }
            },
            update = { playerView ->
                when (lifecycleEvent) {
                    Lifecycle.Event.ON_PAUSE -> {
                        playerView.onPause()
                        playerView.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        playerView.onResume()
                    }

                    else -> Unit
                }
            },
        )

        PostVideoControls(
            isVolumeOff = isVolumeOff,
            onSoundButtonClick = {
                isVolumeOff = !isVolumeOff
                if (isVolumeOff) {
                    exoPlayer.volume = 0f
                } else {
                    exoPlayer.volume = 1f
                }
            },
        )
    }
}

@Composable
fun PostVideoControls(
    isVolumeOff: Boolean,
    onSoundButtonClick: () -> Unit,
) {
    IconButton(
        modifier =
            Modifier.drawBehind {
                drawCircle(color = Color.Black.copy(alpha = 0.5f), radius = 56.0f)
            },
        onClick = onSoundButtonClick,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector =
                if (isVolumeOff) {
                    Icons.AutoMirrored.Filled.VolumeOff
                } else {
                    Icons.AutoMirrored.Filled.VolumeUp
                },
            contentDescription = "Silent",
            tint = Color.White,
        )
    }
}

@Composable
fun PostActions(
    modifier: Modifier = Modifier,
    shouldShowDeleteIcon: Boolean = false,
    upVotes: Int,
    comments: Int,
    isSaved: Boolean,
    onSaveIconClick: () -> Unit = {},
    onDeleteIconClick: () -> Unit = {},
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        PostActionItem(
            icon = Icons.Default.ArrowUpward,
            label = upVotes.toString(),
            actionDescription = stringResource(R.string.upvote),
        )
        PostActionItem(
            icon = Icons.AutoMirrored.Outlined.Message,
            label = comments.toString(),
            actionDescription = stringResource(R.string.comment),
        )

        if (shouldShowDeleteIcon) {
            PostActionItem(
                icon = Icons.Outlined.Delete,
                label = stringResource(R.string.delete),
                actionDescription = stringResource(R.string.delete),
                onclick = onDeleteIconClick,
            )
        } else {
            AnimatedContent(
                targetState = isSaved,
                transitionSpec = {
                    slideIntoContainer(animationSpec = tween(200, easing = EaseIn), towards = Up)
                        .togetherWith(
                            slideOutOfContainer(
                                animationSpec = tween(200, easing = EaseIn),
                                towards = Down,
                            ),
                        )
                },
                label = stringResource(id = R.string.save),
            ) { targetState ->
                PostActionItem(
                    icon = if (targetState) Icons.Default.BookmarkAdded else Icons.Outlined.BookmarkAdd,
                    label =
                        if (targetState) {
                            stringResource(R.string.save)
                        } else {
                            stringResource(R.string.save)
                        },
                    actionDescription = stringResource(R.string.save),
                    onclick = onSaveIconClick,
                )
            }
        }
    }
}

@Composable
fun PostActionItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    actionDescription: String,
    onclick: () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .wrapContentHeight()
                .clickable { onclick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier =
                Modifier
                    .padding(start = 8.dp)
                    .padding(vertical = 8.dp),
            imageVector = icon,
            contentDescription =
                stringResource(R.string.post_action_content_description, actionDescription),
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            text = label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun PostPreview() {
    PostComponent(
        redditPostUiModel = RedditPostUiModel(id = "0"),
        onClick = {},
        onSaveIconClick = {},
    )
}
