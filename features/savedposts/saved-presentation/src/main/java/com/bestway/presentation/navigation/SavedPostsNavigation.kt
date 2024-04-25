package com.bestway.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bestway.common.navigation.Destinations
import com.bestway.presentation.ui.SavedPostsScreen

fun NavGraphBuilder.savedPostsNavGraph(navController: NavHostController) {

    composable(
        route = Destinations.SavedScreenDestination.route,
//        popEnterTransition = {
//            slideInHorizontally(
//                initialOffsetX = { width -> -width },
//                animationSpec = tween(durationMillis = 200, easing = LinearEasing),
//            )
//        },
//        exitTransition = {
//            slideOutHorizontally(
//                targetOffsetX = { width -> -width },
//                animationSpec = tween(durationMillis = 200, easing = LinearEasing),
//            )
//        }
    ) {
        SavedPostsScreen(
            onClick = { postId ->
                navController.navigate(
                    Destinations.ProfileDetailsDestinations.route +
                            "?post-id=$postId"
                )
            }
        )
    }
}
