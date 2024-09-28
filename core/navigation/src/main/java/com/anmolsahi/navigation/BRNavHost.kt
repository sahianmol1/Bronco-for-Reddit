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
    resetScrollAbout: Boolean,
    resetScrollSaved: Boolean,
    resetScrollHome: Boolean,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreenDestination.route,
    ) {
        homeNavGraph(navController = navController, resetScroll = resetScrollHome)
        searchNavGraph(navController = navController, resetScroll = resetScrollSearch)
        savedPostsNavGraph(navController = navController, resetScroll = resetScrollSaved)
        aboutUsNavGraph(resetScroll = resetScrollAbout)
        postDetailsNavGraph(navController = navController)
    }
}
