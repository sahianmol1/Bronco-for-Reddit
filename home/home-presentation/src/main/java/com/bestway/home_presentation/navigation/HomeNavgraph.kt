package com.bestway.home_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bestway.common.navigation.Destinations
import com.bestway.home_presentation.models.RedditPostUiModel
import com.bestway.home_presentation.ui.screens.home.HomeScreen
import com.bestway.home_presentation.ui.screens.postdetails.PostDetailsScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable(route = Destinations.HomeScreenDestination.route) {
        HomeScreen(
            onClick = { redditPost ->
                val redditPostSerialized = Json.encodeToString(redditPost)
                navController.navigate(
                    Destinations.ProfileDetailsDestinations.route +
                        "?reddit-post=$redditPostSerialized"
                )
            }
        )
    }

    composable(
        route = Destinations.ProfileDetailsDestinations.route + "?reddit-post={reddit-post}",
        arguments =
            listOf(
                navArgument("reddit-post") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
    ) { navBackStackEntry ->
        val redditPostSerialized = navBackStackEntry.arguments?.getString("reddit-post")
        val redditPost = Json.decodeFromString<RedditPostUiModel>(redditPostSerialized.orEmpty())
        PostDetailsScreen(redditPostUiModel = redditPost)
    }
}
