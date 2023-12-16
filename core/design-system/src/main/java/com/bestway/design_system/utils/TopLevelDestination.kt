package com.bestway.design_system.utils

import androidx.navigation.NavDestination
import com.bestway.common.navigation.Destinations

private val topLevelDestinations =
    listOf(
        Destinations.HomeScreenDestination.route,
        Destinations.SearchScreenDestination.route,
        Destinations.SubScreenDestination.route,
        Destinations.AboutUsDestination.route
    )

fun NavDestination?.isTopLevelDestination(): Boolean {
    return this?.route in topLevelDestinations
}