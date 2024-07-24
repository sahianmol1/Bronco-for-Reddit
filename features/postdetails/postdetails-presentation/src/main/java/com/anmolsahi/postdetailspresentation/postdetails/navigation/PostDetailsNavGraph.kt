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
            "?post-id={post-id}&is-from-saved-posts={is-from-saved-posts}&post_url={post_url}",
        arguments =
        listOf(
            navArgument("post-id") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument("is-from-saved-posts") {
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
        val isFromSavedPosts = navBackStackEntry.arguments?.getBoolean("is-from-saved-posts")
        val postUrl = navBackStackEntry.arguments?.getString("post_url")
        PostDetailsScreen(
            postId = postId.orEmpty(),
            isFromSavedPosts = isFromSavedPosts ?: false,
            postUrl = postUrl.orEmpty(),
            popBackStack = {
                navController.popBackStack()
            },
        )
    }
}
