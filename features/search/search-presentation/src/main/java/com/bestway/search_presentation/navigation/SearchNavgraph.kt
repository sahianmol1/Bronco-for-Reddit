package com.bestway.search_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bestway.common.navigation.Destinations
import com.bestway.search_presentation.ui.SearchScreen

fun NavGraphBuilder.searchNavGraph(
    navController: NavHostController
) {
    composable(route = Destinations.SearchScreenDestination.route) { SearchScreen() }
}
