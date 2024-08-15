package com.anmolsahi.commonui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun SubRedditName(subName: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .clickable {}
            .padding(top = 8.dp, bottom = 4.dp),
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        text = subName,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun OriginalPosterName(opName: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .clickable {}
            .padding(vertical = 4.dp),
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        text = "u/$opName",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}
