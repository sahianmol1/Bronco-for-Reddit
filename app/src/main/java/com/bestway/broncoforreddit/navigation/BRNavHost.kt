package com.bestway.broncoforreddit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bestway.broncoforreddit.navigation.Destinations.AboutUsDestination
import com.bestway.broncoforreddit.navigation.Destinations.HomeScreenDestination
import com.bestway.broncoforreddit.navigation.Destinations.ProfileDetailsDestinations
import com.bestway.broncoforreddit.navigation.Destinations.SearchScreenDestination
import com.bestway.broncoforreddit.navigation.Destinations.SubScreenDestination
import com.bestway.broncoforreddit.ui.features.about.AboutUsScreen
import com.bestway.broncoforreddit.ui.features.home.screen.HomeScreen
import com.bestway.broncoforreddit.ui.features.postdetails.screen.PostDetailsScreen
import com.bestway.broncoforreddit.ui.features.search.SearchScreen
import com.bestway.broncoforreddit.ui.features.subs.SubsScreen
import com.bestway.broncoforreddit.ui.models.RedditPostUiModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun BRNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreenDestination.route
    ) {
        composable(route = HomeScreenDestination.route) {
            HomeScreen(
                onClick = { redditPost ->
                    val redditPostSerialized = Json.encodeToString(redditPost)
                    navController.navigate(
                        ProfileDetailsDestinations.route + "?reddit-post=$redditPostSerialized"
                    )
                }
            )
        }
        composable(route = SearchScreenDestination.route) { SearchScreen() }
        composable(route = SubScreenDestination.route) { SubsScreen() }
        composable(route = AboutUsDestination.route) { AboutUsScreen() }
        composable(
            route = ProfileDetailsDestinations.route + "?reddit-post={reddit-post}",
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
            PostDetailsScreen(
                redditPostUiModel = redditPost
            )
        }
    }
}
