package com.bestway.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bestway.designsystem.utils.Destinations
import com.bestway.presentation.ui.AboutUsScreen

fun NavGraphBuilder.aboutUsNavGraph(navController: NavHostController) {
    composable(route = Destinations.AboutUsDestination.route) { AboutUsScreen() }
}
