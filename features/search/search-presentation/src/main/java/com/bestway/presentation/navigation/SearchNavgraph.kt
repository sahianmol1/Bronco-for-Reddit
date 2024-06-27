package com.bestway.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bestway.design_system.utils.Destinations
import com.bestway.presentation.ui.SearchScreen

fun NavGraphBuilder.searchNavGraph(navController: NavHostController) {
    composable(route = Destinations.SearchScreenDestination.route) { SearchScreen() }
}
