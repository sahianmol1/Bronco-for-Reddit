package com.bestway.broncoforreddit.utils

import androidx.navigation.NavDestination
import com.bestway.broncoforreddit.navigation.Destinations
import okhttp3.internal.immutableListOf

private val topLevelDestinations =
    immutableListOf(
        Destinations.HomeScreenDestination.route,
        Destinations.SearchScreenDestination.route,
        Destinations.SubScreenDestination.route,
        Destinations.AboutUsDestination.route
    )

fun NavDestination?.isTopLevelDestination(): Boolean {
    return this?.route in topLevelDestinations
}
