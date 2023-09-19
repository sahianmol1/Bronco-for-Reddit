package com.bestway.broncoforreddit.ui.features.home.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class BottomNavUiModel(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
