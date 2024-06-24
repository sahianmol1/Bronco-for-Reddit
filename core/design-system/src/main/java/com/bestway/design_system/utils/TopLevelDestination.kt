package com.bestway.design_system.utils

import androidx.navigation.NavDestination

private val topLevelDestinations =
    listOf(
        Destinations.HomeScreenDestination.route,
        Destinations.SearchScreenDestination.route,
        Destinations.SavedScreenDestination.route,
        Destinations.AboutUsDestination.route
    )

fun NavDestination?.isTopLevelDestination(): Boolean {
    return this?.route in topLevelDestinations
}
