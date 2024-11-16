package com.anmolsahi.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anmolsahi.commonui.utils.serialize
import com.anmolsahi.designsystem.utils.Destinations
import com.anmolsahi.designsystem.utils.isTopLevelDestination
import com.anmolsahi.designsystem.utils.slideInFromBottom
import com.anmolsahi.designsystem.utils.slideInFromLeft
import com.anmolsahi.designsystem.utils.slideOutToLeft
import com.anmolsahi.designsystem.utils.slideOutToTop
import com.anmolsahi.presentation.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    resetScroll: Boolean,
    postScroll: () -> Unit,
) {
    composable(
        route = Destinations.HomeScreenDestination.route,
        enterTransition = {
            if (initialState.destination.isTopLevelDestination()) {
                slideInFromBottom()
            } else {
                slideInFromLeft()
            }
        },
        popEnterTransition = {
            if (initialState.destination.isTopLevelDestination()) {
                slideInFromBottom()
            } else {
                slideInFromLeft()
            }
        },
        exitTransition = {
            if (targetState.destination.isTopLevelDestination()) {
                slideOutToTop()
            } else {
                slideOutToLeft()
            }
        },
        popExitTransition = {
            if (targetState.destination.isTopLevelDestination()) {
                slideOutToTop()
            } else {
                slideOutToLeft()
            }
        },
    ) {
        HomeScreen(
            resetScroll = resetScroll,
            postScroll = postScroll,
            onClick = { postId, postUrl ->
                navController.navigate(
                    Destinations.PostDetailsDestinations.route +
                        "?post-id=$postId&post_url=$postUrl",
                )
            },
            onVideoFullScreenIconClick = { videoUrl ->
                navController.navigate(
                    Destinations.VideoPlayerDestination.route + "?video-url=$videoUrl",
                )
            },
            onImageFullScreenIconClick = { imageList ->
                navController.navigate(
                    Destinations.FullSizeImageDestination.route +
                        "?image-list=${imageList.serialize()}",
                )
            },
        )
    }
}
