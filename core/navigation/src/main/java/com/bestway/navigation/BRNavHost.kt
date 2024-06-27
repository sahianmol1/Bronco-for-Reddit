package com.bestway.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.anmolsahi.postdetailspresentation.postdetails.navigation.postDetailsNavGraph
import com.bestway.design_system.utils.Destinations.HomeScreenDestination
import com.bestway.presentation.navigation.aboutUsNavGraph
import com.bestway.presentation.navigation.homeNavGraph
import com.bestway.presentation.navigation.savedPostsNavGraph
import com.bestway.presentation.navigation.searchNavGraph

@Composable
fun BRNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreenDestination.route
    ) {
        homeNavGraph(navController = navController)
        searchNavGraph(navController = navController)
        savedPostsNavGraph(navController = navController)
        aboutUsNavGraph(navController = navController)
        postDetailsNavGraph(navController = navController)
    }
}
