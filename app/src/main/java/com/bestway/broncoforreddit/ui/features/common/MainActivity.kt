package com.bestway.broncoforreddit.ui.features.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bestway.broncoforreddit.ui.features.home.screen.BroncoForReddit
import com.bestway.broncoforreddit.ui.theme.BroncoForRedditTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent { BroncoForRedditTheme { BroncoForReddit() } }
    }
}
