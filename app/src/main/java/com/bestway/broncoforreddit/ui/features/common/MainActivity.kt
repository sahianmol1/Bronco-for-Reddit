package com.bestway.broncoforreddit.ui.features.common

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.bestway.broncoforreddit.data.api.getHotListings
import com.bestway.broncoforreddit.ui.features.home.BroncoForReddit
import com.bestway.broncoforreddit.ui.theme.BroncoForRedditTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val value = getHotListings()
            Log.i(TAG, "onCreate: $value")
        }

        setContent { BroncoForRedditTheme { BroncoForReddit() } }
    }
}
