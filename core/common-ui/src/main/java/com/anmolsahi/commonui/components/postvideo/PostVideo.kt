package com.anmolsahi.commonui.components.postvideo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.anmolsahi.commonui.components.postvideo.PostVideoConstants.MAX_SCALE
import com.anmolsahi.commonui.components.postvideo.PostVideoConstants.MIN_SCALE
import com.anmolsahi.commonui.utils.rememberLifecycleEvent

private object PostVideoConstants {
    const val MIN_SCALE = 0.5f
    const val MAX_SCALE = 5f
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun PostVideo(videoUrl: String, modifier: Modifier = Modifier, onFullScreenIconClick: () -> Unit) {
    val context = LocalContext.current

    val lifecycleEvent = rememberLifecycleEvent()

    var isVolumeOff by rememberSaveable { mutableStateOf(true) }

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

    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()
            volume = 0f
            play()
            playWhenReady = true
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.BottomStart) {
        DisposableEffect(videoUrl) {
            onDispose {
                exoPlayer.stop()
                exoPlayer.release()
            }
        }

        AndroidView(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .defaultMinSize(minHeight = 300.dp)
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
                        playerView.player?.play()
                        playerView.onResume()
                    }

                    else -> Unit
                }
            },
        )

        if (scale == 1f && offset == Offset.Zero) {
            PostVideoControls(
                isVolumeOff = isVolumeOff,
                onSoundIconClick = {
                    isVolumeOff = !isVolumeOff
                    if (isVolumeOff) {
                        exoPlayer.volume = 0f
                    } else {
                        exoPlayer.volume = 1f
                    }
                },
                onFullScreenIconClick = onFullScreenIconClick,
            )
        }
    }
}
