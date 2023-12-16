package com.bestway.about_presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bestway.about_presentation.ui.AboutUsScreen
import com.bestway.common.navigation.Destinations

fun NavGraphBuilder.aboutUsNavGraph(navController: NavHostController) {

    composable(route = Destinations.AboutUsDestination.route) { AboutUsScreen() }
}