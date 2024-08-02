package com.anmolsahi.designsystem.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anmolsahi.designsystem.R

@Composable
fun BRScrollToTopButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier
            .padding(16.dp)
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.primary, CircleShape)
            .padding(1.dp)
            .background(MaterialTheme.colorScheme.background, CircleShape),
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}

@Composable
fun BRScrollToTopButton2(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .clickable { onClick() },
    ) {
        Icon(
            modifier = Modifier.padding(12.dp),
            imageVector = Icons.Filled.KeyboardArrowUp,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}
