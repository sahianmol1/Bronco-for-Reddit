package com.bestway.broncoforreddit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bestway.broncoforreddit.ui.BroncoForReddit
import com.bestway.broncoforreddit.ui.theme.BroncoForRedditTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent { BroncoForRedditTheme { BroncoForReddit() } }
    }
}
