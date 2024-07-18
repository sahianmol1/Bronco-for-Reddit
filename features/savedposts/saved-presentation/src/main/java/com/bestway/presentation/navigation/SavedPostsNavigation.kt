package com.bestway.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bestway.design_system.utils.Destinations
import com.bestway.presentation.ui.SavedPostsScreen

fun NavGraphBuilder.savedPostsNavGraph(navController: NavHostController) {

    composable(
        route = Destinations.SavedScreenDestination.route,
    ) {
        val isSavedPostsFlow = true
        SavedPostsScreen(
            onClick = { postId ->
                navController.navigate(
                    Destinations.PostDetailsDestinations.route +
                            "?post-id=$postId&is-saved-posts-flow=$isSavedPostsFlow"
                )
            }
        )
    }
}
