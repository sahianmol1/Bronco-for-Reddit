package com.bestway.broncoforreddit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bestway.broncoforreddit.navigation.Screens.AboutUsScreen
import com.bestway.broncoforreddit.navigation.Screens.HomeScreen
import com.bestway.broncoforreddit.navigation.Screens.SearchScreen
import com.bestway.broncoforreddit.navigation.Screens.SubScreen
import com.bestway.broncoforreddit.ui.features.about.AboutUsScreen
import com.bestway.broncoforreddit.ui.features.home.screen.HomeScreen
import com.bestway.broncoforreddit.ui.features.search.SearchScreen
import com.bestway.broncoforreddit.ui.features.subs.SubsScreen

@Composable
fun BRNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = HomeScreen.route) {
        composable(route = HomeScreen.route) {
            HomeScreen(
                onBottomNavItemClicked = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(route = SearchScreen.route) {
            SearchScreen()
        }
        composable(route = SubScreen.route) {
            SubsScreen()
        }
        composable(route = AboutUsScreen.route) {
            AboutUsScreen()
        }
    }
}
