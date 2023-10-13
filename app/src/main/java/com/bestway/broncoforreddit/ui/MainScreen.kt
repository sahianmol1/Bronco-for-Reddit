package com.bestway.broncoforreddit.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.bestway.broncoforreddit.navigation.BRNavHost
import com.bestway.broncoforreddit.ui.components.BRNavigationBar

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

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
