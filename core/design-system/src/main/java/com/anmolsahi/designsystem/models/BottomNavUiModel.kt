package com.anmolsahi.designsystem.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class BottomNavUiModel(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val onClick: () -> Unit,
)
