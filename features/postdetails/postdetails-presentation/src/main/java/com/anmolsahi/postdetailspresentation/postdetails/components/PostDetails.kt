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
import com.anmolsahi.commonui.components.PostActions
import com.anmolsahi.commonui.components.PostImage
import com.anmolsahi.commonui.components.PostVideo
import com.anmolsahi.commonui.components.SubRedditName
import com.anmolsahi.postdetailspresentation.postdetails.ui.postdetails.PostDetailsUiState

@Composable
fun PostDetailsComponent(
    uiState: PostDetailsUiState?,
    isFromSavedPosts: Boolean,
    modifier: Modifier = Modifier,
    onSaveIconClick: () -> Unit,
    onDeleteIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    onFullScreenIconClick: (videoUrl: String) -> Unit,
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

        uiState?.data?.imageUrl?.let {
            if (uiState.data.videoUrl == null) {
                PostImage(
                    modifier = Modifier.zIndex(1f),
                    imageUrl = uiState.data.imageUrl.orEmpty(),
                )
            }
        }

        uiState?.data?.videoUrl?.let { videoUrl ->
            PostVideo(
                modifier = Modifier.zIndex(1f),
                videoUrl = videoUrl,
                onFullScreenIconClick = {
                    onFullScreenIconClick(videoUrl)
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
fun PostDetailsTitle(title: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        fontWeight = FontWeight.Bold,
        text = title,
    )
}

@Composable
fun PostDetailsDescription(description: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        text = description,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.90f),
    )
}
