package com.bestway.subreddit_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bestway.common.navigation.Destinations
import com.bestway.subreddit_presentation.ui.SubsScreen

fun NavGraphBuilder.subredditNavGraph(navController: NavHostController) {

    composable(route = Destinations.SubScreenDestination.route) { SubsScreen() }
}
