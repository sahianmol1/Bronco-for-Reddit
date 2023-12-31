package com.bestway.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bestway.common.navigation.Destinations
import com.bestway.presentation.ui.SubsScreen

fun NavGraphBuilder.subredditNavGraph(navController: NavHostController) {

    composable(route = Destinations.SavedScreenDestination.route) { SubsScreen() }
}
