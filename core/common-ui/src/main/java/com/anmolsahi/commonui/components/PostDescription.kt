package com.anmolsahi.commonui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun PostDescription(description: String, modifier: Modifier = Modifier, maxLines: Int = 3) {
    Text(
        modifier = modifier
            .padding(vertical = 4.dp),
        text = description,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.90f),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
    )
}
