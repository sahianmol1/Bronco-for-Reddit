package com.bestway.broncoforreddit.ui

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bestway.broncoforreddit.navigation.BRNavHost
import com.bestway.broncoforreddit.ui.components.BRNavigationBar

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    val view = LocalView.current
    val navigationBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = navigationBarColor.toArgb()
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Scaffold(
            bottomBar = {
                BRNavigationBar(
                    navController = navController,
                    context = context
                )
            }
        ) { scaffoldPaddingValues ->
            BRNavHost(
                modifier = Modifier
                    .padding(scaffoldPaddingValues),
                navController = navController
            )
        }
    }
}
