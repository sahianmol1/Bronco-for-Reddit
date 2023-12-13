package com.bestway.broncoforreddit.utils

import androidx.navigation.NavDestination
import com.bestway.navigation.Destinations
import okhttp3.internal.immutableListOf

private val topLevelDestinations =
    immutableListOf(
        com.bestway.navigation.Destinations.HomeScreenDestination.route,
        com.bestway.navigation.Destinations.SearchScreenDestination.route,
        com.bestway.navigation.Destinations.SubScreenDestination.route,
        com.bestway.navigation.Destinations.AboutUsDestination.route
    )

fun NavDestination?.isTopLevelDestination(): Boolean {
    return this?.route in topLevelDestinations
}
