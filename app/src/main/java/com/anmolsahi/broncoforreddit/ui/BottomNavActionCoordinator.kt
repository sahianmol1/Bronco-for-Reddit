package com.anmolsahi.broncoforreddit.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.anmolsahi.designsystem.utils.Destinations

class BottomNavActionCoordinator(private val navController: NavHostController) {
    val navigateToHomeNavBar: () -> Unit = {
        navController.navigate(
            Destinations.HomeScreenDestination.route,
            builder = { navConfig(navController) },
        )
    }

    fun navigateToSearchNavBar() {
        navController.navigate(
            Destinations.SearchScreenDestination.route,
            builder = { navConfig(navController) },
        )
    }

    val navigateToSavedPostsNavBar: () -> Unit = {
        navController.navigate(
            Destinations.SavedScreenDestination.route,
            builder = { navConfig(navController) },
        )
    }

    val navigateToAboutNavBar: () -> Unit = {
        navController.navigate(
            Destinations.AboutUsDestination.route,
            builder = { navConfig(navController) },
        )
    }
}

private fun NavOptionsBuilder.navConfig(navController: NavController) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
    // Avoid multiple copies of the same destination when
    // re-selecting the same item
    launchSingleTop = true
    // Restore state when re-selecting a previously selected item
    restoreState = true
}
