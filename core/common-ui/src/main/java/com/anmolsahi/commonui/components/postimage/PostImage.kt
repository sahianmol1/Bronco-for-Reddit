package com.anmolsahi.commonui.components.postimage

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.anmolsahi.commonui.R
import com.anmolsahi.commonui.components.postimage.PostImageConstants.MAX_SCALE
import com.anmolsahi.commonui.components.postimage.PostImageConstants.MIN_SCALE
import com.anmolsahi.designsystem.uicomponents.BRCircularProgressIndicator

private object PostImageConstants {
    const val MIN_SCALE = 0.5f
    const val MAX_SCALE = 5f
}

@Composable
fun PostImage(imageUrl: String, modifier: Modifier = Modifier, onImageClick: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    var isImageLoading by rememberSaveable { mutableStateOf(false) }
    var isImageLoadingError by rememberSaveable { mutableStateOf(false) }

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(MIN_SCALE, MAX_SCALE)
        offset += panChange
    }

    LaunchedEffect(transformableState.isTransformInProgress) {
        if (!transformableState.isTransformInProgress) {
            scale = 1f
            offset = Offset.Zero
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        if (isImageLoading) {
            BRCircularProgressIndicator(modifier = Modifier.padding(vertical = 64.dp))
        }
        AsyncImage(
            modifier = Modifier
                .clickable(role = Role.Image) {
                    onImageClick()
                }
                .padding(vertical = 4.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(
                    state = transformableState,
                    lockRotationOnZoomPan = false,
                    canPan = { currentOffset ->
                        currentOffset == Offset.Zero
                    },
                ),
            model = imageUrl,
            contentScale = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                ContentScale.FillWidth
            } else {
                ContentScale.FillHeight
            },
            onLoading = { isImageLoading = true },
            onSuccess = { isImageLoading = false },
            onError = {
                // TODO: Fix this and use a proper error image
                isImageLoading = false
                isImageLoadingError = true
            },
            contentDescription = stringResource(R.string.reddit_post_image_description),
        )
    }
}
