package com.anmolsahi.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anmolsahi.designsystem.utils.Destinations
import com.anmolsahi.presentation.ui.SearchScreen

fun NavGraphBuilder.searchNavGraph(navController: NavHostController) {
    composable(route = Destinations.SearchScreenDestination.route) {
        SearchScreen(
            onPostClick = { postId, postUrl ->
                navController.navigate(
                    Destinations.PostDetailsDestinations.route +
                            "?post-id=$postId&post_url=$postUrl",
                )
            },
        )
    }
}
