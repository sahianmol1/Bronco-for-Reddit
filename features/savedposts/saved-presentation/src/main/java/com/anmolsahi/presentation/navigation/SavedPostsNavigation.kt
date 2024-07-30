package com.anmolsahi.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anmolsahi.designsystem.utils.Destinations
import com.anmolsahi.presentation.ui.SavedPostsScreen

fun NavGraphBuilder.savedPostsNavGraph(navController: NavHostController) {
    composable(
        route = Destinations.SavedScreenDestination.route,
    ) {
        val isSavedPostsFlow = true
        SavedPostsScreen(
            onClick = { postId, postUrl ->
                navController.navigate(
                    Destinations.PostDetailsDestinations.route +
                        "?post-id=$postId&is-from-saved-posts=$isSavedPostsFlow&post_url=$postUrl",
                )
            },
            onFullScreenIconClick = { videoUrl ->
                navController.navigate(
                    Destinations.VideoPlayerDestination.route + "?video-url=$videoUrl",
                )
            },
        )
    }
}
