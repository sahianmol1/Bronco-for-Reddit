package com.anmolsahi.commonui.components.postactions

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.anmolsahi.commonui.R
import com.anmolsahi.commonui.components.postactions.PostActionConstants.SAVE_ANIMATION_DURATION

private object PostActionConstants {
    const val SAVE_ANIMATION_DURATION = 200
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostActions(
    upVotes: Int,
    comments: Int,
    isSaved: Boolean,
    modifier: Modifier = Modifier,
    shouldShowDeleteIcon: Boolean = false,
    onUpvoteIconClick: () -> Unit = {},
    onCommentIconClick: () -> Unit = {},
    onSaveIconClick: () -> Unit = {},
    onDeleteIconClick: () -> Unit = {},
    onShareIconClick: () -> Unit = {},
) {
    FlowRow(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        PostActionItem(
            icon = Icons.Default.ArrowUpward,
            label = upVotes.toString(),
            actionDescription = stringResource(R.string.upvote),
            onclick = onUpvoteIconClick,
        )
        PostActionItem(
            icon = Icons.AutoMirrored.Outlined.Message,
            label = comments.toString(),
            actionDescription = stringResource(R.string.comment),
            onclick = onCommentIconClick,
        )

        PostActionItem(
            icon = Icons.Outlined.Share,
            label = stringResource(id = R.string.share),
            actionDescription = stringResource(id = R.string.share),
            onclick = onShareIconClick,
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
                    slideIntoContainer(
                        animationSpec = tween(SAVE_ANIMATION_DURATION, easing = EaseIn),
                        towards = Up,
                    )
                        .togetherWith(
                            slideOutOfContainer(
                                animationSpec = tween(SAVE_ANIMATION_DURATION, easing = EaseIn),
                                towards = Down,
                            ),
                        )
                },
                label = stringResource(id = R.string.save),
            ) { targetState ->
                PostActionItem(
                    icon = if (targetState) {
                        Icons.Default.BookmarkAdded
                    } else {
                        Icons.Outlined.BookmarkAdd
                    },
                    label = if (targetState) {
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
