package com.anmolsahi.data.datastore

import androidx.datastore.preferences.core.longPreferencesKey

object PreferencesKeys {

    private const val HOT_POSTS_TIMESTAMP = "hot_posts_timestamp"
    private const val TOP_POSTS_TIMESTAMP = "top_posts_timestamp"
    private const val RISING_POSTS_TIMESTAMP = "rising_posts_timestamp"
    private const val BEST_POSTS_TIMESTAMP = "best_posts_timestamp"
    private const val CONTROVERSIAL_POSTS_TIMESTAMP = "controversial_posts_timestamp"
    private const val NEW_POSTS_TIMESTAMP = "new_posts_timestamp"

    val hotPostsKey = longPreferencesKey(HOT_POSTS_TIMESTAMP)
    val topPostsKey = longPreferencesKey(TOP_POSTS_TIMESTAMP)
    val risingPostsKey = longPreferencesKey(RISING_POSTS_TIMESTAMP)
    val bestPostsKey = longPreferencesKey(BEST_POSTS_TIMESTAMP)
    val controversialPostsKey = longPreferencesKey(CONTROVERSIAL_POSTS_TIMESTAMP)
    val newPostsKey = longPreferencesKey(NEW_POSTS_TIMESTAMP)
}
