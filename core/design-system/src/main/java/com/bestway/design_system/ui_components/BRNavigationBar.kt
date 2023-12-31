package com.bestway.design_system.ui_components

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bestway.common.navigation.Destinations
import com.bestway.design_system.R
import com.bestway.design_system.models.BottomNavUiModel
import com.bestway.design_system.utils.isTopLevelDestination

@Composable
fun BRNavigationBar(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavHostController
) {
    val bottomNavItems by remember {
        mutableStateOf(
            listOf(
                BottomNavUiModel(
                    route = Destinations.HomeScreenDestination.route,
                    title = context.getString(R.string.home),
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home
                ),
                BottomNavUiModel(
                    route = Destinations.SearchScreenDestination.route,
                    title = context.getString(R.string.search),
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search
                ),
                BottomNavUiModel(
                    route = Destinations.SavedScreenDestination.route,
                    title = context.getString(R.string.saved),
                    selectedIcon = Icons.Filled.Star,
                    unselectedIcon = Icons.Outlined.StarOutline
                ),
                BottomNavUiModel(
                    route = Destinations.AboutUsDestination.route,
                    title = context.getString(R.string.about),
                    selectedIcon = Icons.Filled.Info,
                    unselectedIcon = Icons.Outlined.Info
                ),
            )
        )
    }

    BRNavigationBarView(
        navController = navController,
        modifier = modifier,
        bottomNavItems = bottomNavItems,
    )
}

@Composable
fun BRNavigationBarView(
    modifier: Modifier = Modifier,
    bottomNavItems: List<BottomNavUiModel>,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination by
        remember(navBackStackEntry) { mutableStateOf(navBackStackEntry?.destination) }

    AnimatedVisibility(
        visible = currentDestination.isTopLevelDestination(),
        enter =
            slideInVertically(
                // Enters by sliding up from offset 0 to fullHeight.
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            ),
        exit =
            slideOutVertically(
                // Exits by sliding up from offset 0 to -fullHeight.
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 400, easing = FastOutLinearInEasing),
            ),
    ) {
        NavigationBar(modifier = modifier) {
            bottomNavItems.forEachIndexed { index, bottomNav ->
                NavigationBarItem(
                    selected = getSelectedBottomNav(currentDestination, bottomNavItems, index),
                    onClick = {
                        navController.navigate(bottomNavItems[index].route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector =
                                if (
                                    getSelectedBottomNav(currentDestination, bottomNavItems, index)
                                ) {
                                    bottomNav.selectedIcon
                                } else {
                                    bottomNav.unselectedIcon
                                },
                            contentDescription =
                                stringResource(
                                    R.string.bottom_bar_content_description,
                                    bottomNav.title
                                )
                        )
                    },
                    label = { Text(text = bottomNav.title) }
                )
            }
        }
    }
}

private fun getSelectedBottomNav(
    currentDestination: NavDestination?,
    bottomNavItems: List<BottomNavUiModel>,
    index: Int
): Boolean {
    return currentDestination?.hierarchy?.any { it.route == bottomNavItems[index].route } == true
}
