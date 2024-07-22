package com.anmolsahi.broncoforreddit.ui.features.home.screen

import android.app.Activity
import android.graphics.Color
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anmolsahi.designsystem.uicomponents.BRNavigationBar
import com.anmolsahi.designsystem.utils.isTopLevelDestination
import com.anmolsahi.navigation.BRNavHost

@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination by remember(navBackStackEntry) { mutableStateOf(navBackStackEntry?.destination) }

    val view = LocalView.current
    val navigationBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor =
                if (currentDestination.isTopLevelDestination()) {
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
                    context = context,
                )
            },
            contentWindowInsets = WindowInsets(left = 0.dp, top = 0.dp, right = 0.dp, bottom = 0.dp),
        ) { scaffoldPaddingValues ->
            BRNavHost(
                modifier = Modifier.padding(scaffoldPaddingValues),
                navController = navController,
            )
        }
    }
}
