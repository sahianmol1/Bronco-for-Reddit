package com.anmolsahi.commonui.components.postvideo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anmolsahi.commonui.R
import com.anmolsahi.commonui.components.postimage.PostImage

@Composable
fun PostVideoThumbnail(modifier: Modifier = Modifier, thumbnailUrl: String, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .heightIn(max = 300.dp),
        contentAlignment = Alignment.BottomStart,
    ) {
        PostImage(
            imageUrl = thumbnailUrl,
            onImageClick = onClick,
        )

        ElevatedAssistChip(
            modifier = Modifier.padding(4.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = stringResource(
                        R.string.reddit_video_thumbnail_content_description,
                    ),
                )
            },
            label = {
                Text(stringResource(R.string.reddit_video))
            },
            onClick = onClick,
        )
    }
}
