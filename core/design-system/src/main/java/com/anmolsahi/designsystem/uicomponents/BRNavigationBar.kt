package com.anmolsahi.designsystem.uicomponents

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anmolsahi.designsystem.R
import com.anmolsahi.designsystem.models.BottomNavUiModel
import com.anmolsahi.designsystem.theme.BRTheme
import com.anmolsahi.designsystem.utils.isTopLevelDestination

@Composable
fun BRNavigationBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavUiModel>,
    navController: NavHostController,
) {
    BRNavigationBarView(
        navController = navController,
        modifier = modifier,
        bottomNavItems = items,
    )
}

@Composable
fun BRNavigationBarView(
    modifier: Modifier = Modifier,
    bottomNavItems: List<BottomNavUiModel>,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination by
        remember(navBackStackEntry) { mutableStateOf(navBackStackEntry?.destination) }

    if (currentDestination.isTopLevelDestination()) {
        NavigationBar(
            modifier = modifier,
            containerColor = BRTheme.colorScheme.surfaceContainer,
        ) {
            bottomNavItems.forEachIndexed { index, bottomNav ->
                NavigationBarItem(
                    selected = getSelectedBottomNav(currentDestination, bottomNavItems, index),
                    onClick = bottomNav.onClick,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BRTheme.colorScheme.onPrimaryContainer,
                        unselectedIconColor = BRTheme.colorScheme.onSurfaceVariant,
                        selectedTextColor = BRTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = BRTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = BRTheme.colorScheme.primaryContainer,
                    ),
                    icon = {
                        Icon(
                            imageVector = if (
                                getSelectedBottomNav(currentDestination, bottomNavItems, index)
                            ) {
                                bottomNav.selectedIcon
                            } else {
                                bottomNav.unselectedIcon
                            },
                            contentDescription = stringResource(
                                R.string.bottom_bar_content_description,
                                bottomNav.title,
                            ),
                        )
                    },
                    label = { Text(text = bottomNav.title) },
                )
            }
        }
    }
}

private fun getSelectedBottomNav(
    currentDestination: NavDestination?,
    bottomNavItems: List<BottomNavUiModel>,
    index: Int,
): Boolean = currentDestination?.hierarchy?.any { it.route == bottomNavItems[index].route } == true
