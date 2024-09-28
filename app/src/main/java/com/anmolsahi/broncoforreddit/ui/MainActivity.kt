package com.anmolsahi.broncoforreddit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.anmolsahi.broncoforreddit.ui.features.home.screen.Bronco
import com.anmolsahi.broncoforreddit.ui.features.home.screen.MainViewModel
import com.anmolsahi.designsystem.theme.BRTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            BRTheme {
                val navController = rememberNavController()
                val navigator = BottomNavActionCoordinator(navController)
                Bronco(
                    navController = navController,
                    viewModel = viewModel,
                    bottomTabsNavigator = navigator,
                )
            }
        }
    }
}
