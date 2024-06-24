package com.bestway.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anmolsahi.postdetailspresentation.postdetails.ui.PostDetailsScreen
import com.bestway.design_system.utils.Destinations
import com.bestway.presentation.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable(
        route = Destinations.HomeScreenDestination.route,
    ) {
        HomeScreen(
            onClick = { postId ->
                navController.navigate(
                    Destinations.PostDetailsDestinations.route + "?post-id=$postId"
                )
            }
        )
    }

    composable(
        route = Destinations.PostDetailsDestinations.route + "?post-id={post-id}",
        arguments =
        listOf(
            navArgument("post-id") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) { navBackStackEntry ->
        val postId = navBackStackEntry.arguments?.getString("post-id")
        PostDetailsScreen(postId = postId.orEmpty())
    }
}
