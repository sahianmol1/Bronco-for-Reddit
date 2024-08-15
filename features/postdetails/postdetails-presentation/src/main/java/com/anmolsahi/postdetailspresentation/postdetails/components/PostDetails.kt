package com.anmolsahi.postdetailspresentation.postdetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.anmolsahi.commonui.components.OriginalPosterName
import com.anmolsahi.commonui.components.SubRedditName
import com.anmolsahi.commonui.components.postactions.PostActions
import com.anmolsahi.commonui.components.postimage.PostImage
import com.anmolsahi.commonui.components.postimage.PostImagePager
import com.anmolsahi.commonui.components.postvideo.PostVideo
import com.anmolsahi.postdetailspresentation.postdetails.ui.postdetails.PostDetailsUiState

@Composable
internal fun PostDetailsComponent(
    uiState: PostDetailsUiState?,
    isFromSavedPosts: Boolean,
    modifier: Modifier = Modifier,
    onSaveIconClick: () -> Unit,
    onDeleteIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    onVideoFullScreenIconClick: (videoUrl: String) -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        SubRedditName(
            subName = uiState?.data?.subName.orEmpty(),
        )

        OriginalPosterName(
            opName = uiState?.data?.author.orEmpty(),
        )

        if (!uiState?.data?.title.isNullOrBlank()) {
            PostDetailsTitle(title = uiState?.data?.title.orEmpty())
        }

        if (!uiState?.data?.description.isNullOrBlank()) {
            PostDetailsDescription(
                description = uiState?.data?.description.orEmpty(),
            )
        }

        uiState?.data?.imageUrlList?.apply {
            if (uiState.data.videoUrl == null) {
                if (this.size == 1) {
                    PostImage(
                        modifier = Modifier
                            .zIndex(1f),
                        imageUrl = this.first().orEmpty(),
                    )
                } else if (this.size > 1) {
                    PostImagePager(
                        modifier = Modifier
                            .zIndex(1f),
                        imageUrlList = this.filterNotNull(),
                        onFullScreenIconClick = {
                            onImageFullScreenIconClick(this.filterNotNull())
                        },
                    )
                }
            }
        }

        uiState?.data?.videoUrl?.let { videoUrl ->
            PostVideo(
                modifier = Modifier.zIndex(1f),
                videoUrl = videoUrl,
                onFullScreenIconClick = {
                    onVideoFullScreenIconClick(videoUrl)
                },
            )
        }

        PostActions(
            modifier = Modifier.padding(top = 8.dp),
            upVotes = uiState?.data?.upVotes ?: 0,
            comments = uiState?.data?.comments ?: 0,
            isSaved = uiState?.data?.isSaved ?: false,
            shouldShowDeleteIcon = isFromSavedPosts,
            onSaveIconClick = onSaveIconClick,
            onDeleteIconClick = onDeleteIconClick,
            onShareIconClick = onShareIconClick,
        )
    }
}

@Composable
private fun PostDetailsTitle(title: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        fontWeight = FontWeight.Bold,
        text = title,
    )
}

@Composable
private fun PostDetailsDescription(description: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        text = description,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.90f),
    )
}
