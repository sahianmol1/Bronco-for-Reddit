package com.anmolsahi.commonui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun PostTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .padding(vertical = 4.dp),
        fontWeight = FontWeight.Bold,
        text = title,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}
