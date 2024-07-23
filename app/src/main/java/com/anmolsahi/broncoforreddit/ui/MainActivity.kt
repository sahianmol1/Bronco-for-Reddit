package com.anmolsahi.broncoforreddit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.anmolsahi.broncoforreddit.ui.features.home.screen.MainScreen
import com.anmolsahi.designsystem.theme.BroncoForRedditTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            BroncoForRedditTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController)
            }
        } }
}