package com.anmolsahi.postdetailspresentation.postdetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anmolsahi.designsystem.utils.Destinations
import com.anmolsahi.postdetailspresentation.postdetails.ui.PostDetailsScreen

fun NavGraphBuilder.postDetailsNavGraph(navController: NavHostController) {
    composable(
        route =
            Destinations.PostDetailsDestinations.route +
                "?post-id={post-id}&is-saved-posts-flow={is-saved-posts-flow}&post_url={post_url}",
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
                navArgument("post_url") {
                    type = NavType.StringType
                    nullable = true
                },
            ),
    ) { navBackStackEntry ->
        val postId = navBackStackEntry.arguments?.getString("post-id")
        val isSavedPostsFlow = navBackStackEntry.arguments?.getBoolean("is-saved-posts-flow")
        val postUrl = navBackStackEntry.arguments?.getString("post_url")
        PostDetailsScreen(
            postId = postId.orEmpty(),
            isSavedPostsFlow = isSavedPostsFlow ?: false,
            postUrl = postUrl.orEmpty(),
            popBackStack = {
                navController.popBackStack()
            },
        )
    }
}
