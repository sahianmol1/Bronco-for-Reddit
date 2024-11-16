package com.anmolsahi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.anmolsahi.designsystem.utils.Destinations.HomeScreenDestination
import com.anmolsahi.postdetailspresentation.postdetails.navigation.postDetailsNavGraph
import com.anmolsahi.presentation.navigation.aboutUsNavGraph
import com.anmolsahi.presentation.navigation.homeNavGraph
import com.anmolsahi.presentation.navigation.savedPostsNavGraph
import com.anmolsahi.presentation.navigation.searchNavGraph

@Composable
fun BRNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    resetScrollSearch: Boolean,
    postScrollSearch: () -> Unit,
    resetScrollAbout: Boolean,
    postScrollAbout: () -> Unit,
    resetScrollSaved: Boolean,
    postScrollSaved: () -> Unit,
    resetScrollHome: Boolean,
    postScrollHome: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreenDestination.route,
    ) {
        homeNavGraph(
            navController = navController,
            resetScroll = resetScrollHome,
            postScroll = postScrollHome,
        )
        searchNavGraph(
            navController = navController,
            resetScroll = resetScrollSearch,
            postScroll = postScrollSearch,
        )
        savedPostsNavGraph(
            navController = navController,
            resetScroll = resetScrollSaved,
            postScroll = postScrollSaved,
        )
        aboutUsNavGraph(resetScroll = resetScrollAbout, postScroll = postScrollAbout)
        postDetailsNavGraph(navController = navController)
    }
}
