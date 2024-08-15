package com.anmolsahi.commonui.components.postvideo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anmolsahi.commonui.R

@Composable
fun PostVideoControls(
    isVolumeOff: Boolean,
    modifier: Modifier = Modifier,
    onSoundIconClick: () -> Unit,
    onFullScreenIconClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            modifier = Modifier.drawBehind {
                drawCircle(color = Color.Black.copy(alpha = 0.5f), radius = 56.0f)
            },
            onClick = onFullScreenIconClick,
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Fullscreen,
                contentDescription = stringResource(R.string.go_fullscreen),
                tint = Color.White,
            )
        }

        IconButton(
            modifier = Modifier.drawBehind {
                drawCircle(color = Color.Black.copy(alpha = 0.5f), radius = 56.0f)
            },
            onClick = onSoundIconClick,
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector =
                if (isVolumeOff) {
                    Icons.AutoMirrored.Filled.VolumeOff
                } else {
                    Icons.AutoMirrored.Filled.VolumeUp
                },
                contentDescription = stringResource(R.string.silent),
                tint = Color.White,
            )
        }
    }
}
