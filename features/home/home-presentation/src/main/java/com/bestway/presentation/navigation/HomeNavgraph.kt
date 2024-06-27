package com.bestway.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bestway.design_system.utils.Destinations
import com.bestway.presentation.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable(
        route = Destinations.HomeScreenDestination.route,
    ) {
        HomeScreen(
            onClick = { postId ->
                navController.navigate(
                    Destinations.PostDetailsDestinations.route + "?post-id=$postId",
                )
            },
        )
    }
}
