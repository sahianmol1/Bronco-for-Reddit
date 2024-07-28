package com.anmolsahi.commonui.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.anmolsahi.commonui.R

fun shareRedditPost(postUrl: String, context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Check out this post on reddit: https://www.reddit.com$postUrl")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(
        sendIntent,
        ContextCompat.getString(context, R.string.share),
    )

    context.startActivity(shareIntent)
}
