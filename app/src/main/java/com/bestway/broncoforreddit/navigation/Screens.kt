package com.bestway.broncoforreddit.navigation

import com.bestway.broncoforreddit.navigation.Destinations.ABOUT_SCREEN_ROUTE
import com.bestway.broncoforreddit.navigation.Destinations.HOME_SCREEN_ROUTE
import com.bestway.broncoforreddit.navigation.Destinations.SEARCH_SCREEN_ROUTE
import com.bestway.broncoforreddit.navigation.Destinations.SUB_SCREEN_ROUTE

sealed class Screens(val route: String) {
    object HomeScreen: Screens(route = HOME_SCREEN_ROUTE)
    object SearchScreen: Screens(route = SEARCH_SCREEN_ROUTE)

    object SubScreen: Screens(route = SUB_SCREEN_ROUTE)

    object AboutUsScreen: Screens(route = ABOUT_SCREEN_ROUTE)
}

object Destinations {
    const val HOME_SCREEN_ROUTE = "home_screen"
    const val SEARCH_SCREEN_ROUTE = "search_screen"
    const val SUB_SCREEN_ROUTE = "sub_screen"
    const val ABOUT_SCREEN_ROUTE = "about_us"

}