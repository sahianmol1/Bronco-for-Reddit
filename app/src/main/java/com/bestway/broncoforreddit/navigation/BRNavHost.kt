package com.bestway.broncoforreddit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun BRNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreen.route
    ) {
        composable(route = HomeScreen.route) { HomeScreen() }
        composable(route = SearchScreen.route) { SearchScreen() }
        composable(route = SubScreen.route) { SubsScreen() }
        composable(route = AboutUsScreen.route) { AboutUsScreen() }
    }
}
