package com.anmolsahi.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anmolsahi.designsystem.utils.Destinations
import com.anmolsahi.presentation.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable(
        route = Destinations.HomeScreenDestination.route,
    ) {
        HomeScreen(
            onClick = { postId, postUrl ->
                navController.navigate(
                    Destinations.PostDetailsDestinations.route +
                        "?post-id=$postId&post_url=$postUrl",
                )
            },
        )
    }
}
