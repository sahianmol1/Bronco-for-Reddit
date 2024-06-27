package com.anmolsahi.postdetailspresentation.postdetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anmolsahi.postdetailspresentation.postdetails.ui.PostDetailsScreen
import com.bestway.design_system.utils.Destinations

fun NavGraphBuilder.postDetailsNavGraph(navController: NavHostController) {
    composable(
        route = Destinations.PostDetailsDestinations.route +
                "?post-id={post-id}&is-saved-posts-flow={is-saved-posts-flow}",
        arguments =
        listOf(
            navArgument("post-id") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument("is-saved-posts-flow") {
                type = NavType.BoolType
                defaultValue = false
            },
        ),
    ) { navBackStackEntry ->
        val postId = navBackStackEntry.arguments?.getString("post-id")
        val isSavedPostsFlow = navBackStackEntry.arguments?.getBoolean("is-saved-posts-flow")
        PostDetailsScreen(
            postId = postId.orEmpty(),
            isSavedPostsFlow = isSavedPostsFlow ?: false,
            popBackStack = {
                navController.popBackStack()
            }
        )
    }
}