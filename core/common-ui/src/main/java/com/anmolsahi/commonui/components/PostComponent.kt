package com.anmolsahi.commonui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.anmolsahi.commonui.components.postactions.PostActions
import com.anmolsahi.commonui.components.postimage.PostImage
import com.anmolsahi.commonui.components.postimage.PostImagePager
import com.anmolsahi.commonui.components.postvideo.PostVideo
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.datadog.android.rum.GlobalRumMonitor
import com.datadog.android.rum.RumActionType

@Composable
fun PostComponent(
    modifier: Modifier = Modifier,
    redditPostUiModel: RedditPostUiModel,
    shouldShowDeleteIcon: Boolean = false,
    onClick: (postId: String, postUrl: String) -> Unit,
    onSaveIconClick: (postId: String) -> Unit,
    onDeleteIconClick: (postId: String) -> Unit = {},
    onShareIconClick: (postUrl: String) -> Unit = {},
    onImageFullScreenIconClick: (List<String>) -> Unit = {},
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
) {
    Column(
        modifier = modifier
            .animateContentSize()
            .clickable { onClick(redditPostUiModel.id, redditPostUiModel.postUrl.orEmpty()) }
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

        redditPostUiModel.imageUrlList?.apply {
            if (redditPostUiModel.videoUrl == null) {
                if (this.size == 1) {
                    PostImage(
                        modifier = Modifier
                            .zIndex(1f),
                        imageUrl = this.first().orEmpty(),
                        onImageClick = {
                            onClick(redditPostUiModel.id, redditPostUiModel.postUrl.orEmpty())
                        },
                    )
                } else if (this.size > 1) {
                    PostImagePager(
                        modifier = Modifier
                            .zIndex(1f),
                        imageUrlList = this.filterNotNull(),
                        onImageClick = {
                            onClick(redditPostUiModel.id, redditPostUiModel.postUrl.orEmpty())
                        },
                        onFullScreenIconClick = {
                            onImageFullScreenIconClick(this.filterNotNull())
                        },
                    )
                }
            }
        }

        redditPostUiModel.videoUrl?.let {
            PostVideo(
                modifier = Modifier.zIndex(1f),
                videoUrl = it,
                onFullScreenIconClick = {
                    onVideoFullScreenIconClick(redditPostUiModel.videoUrl)
                },
            )
        }

        PostActions(
            modifier = Modifier.padding(top = 8.dp),
            upVotes = redditPostUiModel.upVotes,
            comments = redditPostUiModel.comments,
            isSaved = redditPostUiModel.isSaved,
            shouldShowDeleteIcon = shouldShowDeleteIcon,
            onSaveIconClick = {
                GlobalRumMonitor.get().addAction(
                    type = RumActionType.TAP,
                    name = "onSaveIconClick",
                    attributes = mapOf("postId" to redditPostUiModel.title),
                )
                onSaveIconClick(redditPostUiModel.id)
            },
            onDeleteIconClick = { onDeleteIconClick(redditPostUiModel.id) },
            onShareIconClick = { onShareIconClick(redditPostUiModel.postUrl.orEmpty()) },
            onUpvoteIconClick = {
                onClick(redditPostUiModel.id, redditPostUiModel.postUrl.orEmpty())
            },
            onCommentIconClick = {
                onClick(redditPostUiModel.id, redditPostUiModel.postUrl.orEmpty())
            },
        )
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview
@Composable
private fun PostPreview() {
    PostComponent(
        redditPostUiModel = RedditPostUiModel(id = "0"),
        onClick = { _, _ -> },
        onSaveIconClick = {},
        onVideoFullScreenIconClick = { _ -> },
    )
}
