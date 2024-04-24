package com.bestway.presentation.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anmolsahi.common_ui.screens.postdetails.PostDetailsScreen
import com.bestway.common.navigation.Destinations
import com.bestway.presentation.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable(
        route = Destinations.HomeScreenDestination.route,
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { width -> -width },
                animationSpec = tween(durationMillis = 200, easing = LinearEasing),
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { width -> -width },
                animationSpec = tween(durationMillis = 200, easing = LinearEasing),
            )
        }
    ) {
        HomeScreen(
            onClick = { postId ->
                navController.navigate(
                    Destinations.ProfileDetailsDestinations.route +
                            "?post-id=$postId"
                )
            }
        )
    }

    composable(
        route = Destinations.ProfileDetailsDestinations.route + "?post-id={post-id}",
        arguments =
        listOf(
            navArgument("post-id") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { width -> width },
                animationSpec = tween(durationMillis = 200, easing = LinearEasing),
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { width -> width },
                animationSpec = tween(durationMillis = 200, easing = LinearEasing),
            )
        },
    ) { navBackStackEntry ->
        val postId = navBackStackEntry.arguments?.getString("post-id")
        PostDetailsScreen(postId = postId.orEmpty())
    }
}
