package com.anmolsahi.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anmolsahi.designsystem.utils.Destinations
import com.anmolsahi.presentation.ui.AboutUsScreen

fun NavGraphBuilder.aboutUsNavGraph(navController: NavHostController) {
    composable(route = Destinations.AboutUsDestination.route) { AboutUsScreen() }
}
