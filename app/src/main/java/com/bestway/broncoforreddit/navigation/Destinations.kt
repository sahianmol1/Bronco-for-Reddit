package com.bestway.broncoforreddit.navigation

import com.bestway.broncoforreddit.navigation.Routes.ABOUT_SCREEN_ROUTE
import com.bestway.broncoforreddit.navigation.Routes.HOME_SCREEN_ROUTE
import com.bestway.broncoforreddit.navigation.Routes.PROFILE_DETAILS_ROUTE
import com.bestway.broncoforreddit.navigation.Routes.SEARCH_SCREEN_ROUTE
import com.bestway.broncoforreddit.navigation.Routes.SUB_SCREEN_ROUTE

sealed class Destinations(val route: String) {
    object HomeScreenDestination: Destinations(route = HOME_SCREEN_ROUTE)

    object SearchScreenDestination: Destinations(route = SEARCH_SCREEN_ROUTE)

    object SubScreenDestination: Destinations(route = SUB_SCREEN_ROUTE)

    object AboutUsDestination: Destinations(route = ABOUT_SCREEN_ROUTE)

    object ProfileDetailsDestinations: Destinations(route = PROFILE_DETAILS_ROUTE)
}

object Routes {
    const val HOME_SCREEN_ROUTE = "home_screen"
    const val SEARCH_SCREEN_ROUTE = "search_screen"
    const val SUB_SCREEN_ROUTE = "sub_screen"
    const val ABOUT_SCREEN_ROUTE = "about_us"
    const val PROFILE_DETAILS_ROUTE = "profile_details_route"
}