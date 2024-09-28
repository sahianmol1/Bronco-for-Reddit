package com.anmolsahi.broncoforreddit.ui.features.home.screen

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anmolsahi.broncoforreddit.ui.BottomNavActionCoordinator
import com.anmolsahi.designsystem.R
import com.anmolsahi.designsystem.models.BottomNavUiModel
import com.anmolsahi.designsystem.uicomponents.BRNavigationBar
import com.anmolsahi.designsystem.utils.Destinations
import com.anmolsahi.designsystem.utils.isTopLevelDestination
import com.anmolsahi.navigation.BRNavHost

@Composable
fun Bronco(
    navController: NavHostController,
    viewModel: MainViewModel,
    bottomTabsNavigator: BottomNavActionCoordinator,
) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination by remember(navBackStackEntry) {
        mutableStateOf(navBackStackEntry?.destination)
    }

    val view = LocalView.current
    val configuration = LocalConfiguration.current
    val navigationBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    val resetScrollHome
        by remember { viewModel.resetScrollHome }.collectAsStateWithLifecycle(false)
    val resetScrollSearch
        by remember { viewModel.resetScrollSearch }.collectAsStateWithLifecycle(false)
    val resetScrollSaved
        by remember { viewModel.resetScrollSaved }.collectAsStateWithLifecycle(false)
    val resetScrollAbout
        by remember { viewModel.resetScrollAbout }.collectAsStateWithLifecycle(false)

    val bottomNavItems by remember {
        mutableStateOf(
            listOf(
                BottomNavUiModel(
                    route = Destinations.HomeScreenDestination.route,
                    title = context.getString(R.string.home),
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    onClick = {
                        if (navController.currentDestination?.route ==
                            Destinations.HomeScreenDestination.route
                        ) {
                            viewModel.resetScrollHome()
                        } else {
                            bottomTabsNavigator.navigateToHomeNavBar
                        }
                    },
                ),
                BottomNavUiModel(
                    route = Destinations.SearchScreenDestination.route,
                    title = context.getString(R.string.search),
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search,
                    onClick = {
                        if (navController.currentDestination?.route ==
                            Destinations.SearchScreenDestination.route
                        ) {
                            viewModel.resetScrollSearch()
                        } else {
                            bottomTabsNavigator.navigateToSearchNavBar()
                        }
                    },
                ),
                BottomNavUiModel(
                    route = Destinations.SavedScreenDestination.route,
                    title = context.getString(R.string.saved),
                    selectedIcon = Icons.Filled.Bookmarks,
                    unselectedIcon = Icons.Outlined.Bookmarks,
                    onClick = {
                        if (navController.currentDestination?.route ==
                            Destinations.SavedScreenDestination.route
                        ) {
                            viewModel.resetScrollSaved()
                        } else {
                            bottomTabsNavigator.navigateToSavedPostsNavBar
                        }
                    },
                ),
                BottomNavUiModel(
                    route = Destinations.AboutUsDestination.route,
                    title = context.getString(R.string.about),
                    selectedIcon = Icons.Filled.Info,
                    unselectedIcon = Icons.Outlined.Info,
                    onClick = {
                        if (navController.currentDestination?.route ==
                            Destinations.AboutUsDestination.route
                        ) {
                            viewModel.resetScrollAbout()
                        } else {
                            bottomTabsNavigator.navigateToAboutNavBar
                        }
                    },
                ),
            ),
        )
    }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor =
                if (
                    currentDestination.isTopLevelDestination() &&
                    configuration.orientation == Configuration.ORIENTATION_PORTRAIT
                ) {
                    navigationBarColor.toArgb()
                } else {
                    Color.TRANSPARENT
                }
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Scaffold(
            bottomBar = {
                BRNavigationBar(
                    navController = navController,
                    items = bottomNavItems,
                )
            },
            contentWindowInsets = WindowInsets(
                left = 0.dp,
                top = 0.dp,
                right = 0.dp,
                bottom = 0.dp,
            ),
        ) { scaffoldPaddingValues ->
            BRNavHost(
                modifier = Modifier.padding(scaffoldPaddingValues),
                navController = navController,
                resetScrollSearch = resetScrollSearch,
                resetScrollAbout = resetScrollAbout,
                resetScrollSaved = resetScrollSaved,
                resetScrollHome = resetScrollHome,
            )
        }
    }
}
