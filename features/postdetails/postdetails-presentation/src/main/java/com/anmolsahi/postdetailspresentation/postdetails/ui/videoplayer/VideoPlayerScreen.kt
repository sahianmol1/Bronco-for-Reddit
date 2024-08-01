package com.anmolsahi.postdetailspresentation.postdetails.ui.videoplayer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.anmolsahi.commonui.utils.ErrorDialog
import com.anmolsahi.commonui.utils.rememberLifecycleEvent
import dagger.hilt.android.UnstableApi

/**
 * This is the full screen Video player View/Screen
 */
@Composable
fun VideoPlayerScreen(modifier: Modifier = Modifier, videoUrl: String? = null) {
    if (videoUrl == null) {
        ErrorDialog(errorMessage = "Video URL is null")
    } else {
        VideoPlayerView(modifier = modifier, videoUrl = videoUrl)
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoPlayerView(videoUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val lifecycleEvent = rememberLifecycleEvent()

    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()
            volume = 1.0f
            play()
            playWhenReady = true
        }
    }

    DisposableEffect(videoUrl) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier
            .padding(vertical = 4.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize(),
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
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
}
