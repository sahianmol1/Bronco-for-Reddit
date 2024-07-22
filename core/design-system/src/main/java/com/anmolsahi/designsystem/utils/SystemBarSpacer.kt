package com.anmolsahi.designsystem.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun StatusBarSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .windowInsetsTopHeight(WindowInsets.statusBars)
            .fillMaxWidth()
            .background(Color.Transparent),
    )
}
