package com.bestway.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bestway.about_presentation.navigation.aboutUsNavGraph
import com.bestway.common.navigation.Destinations.HomeScreenDestination
import com.bestway.home_presentation.navigation.homeNavGraph
import com.bestway.search_presentation.navigation.searchNavGraph
import com.bestway.subreddit_presentation.navigation.subredditNavGraph

@Composable
fun BRNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreenDestination.route
    ) {
        homeNavGraph(navController = navController)
        searchNavGraph(navController = navController)
        subredditNavGraph(navController = navController)
        aboutUsNavGraph(navController = navController)
    }
}
