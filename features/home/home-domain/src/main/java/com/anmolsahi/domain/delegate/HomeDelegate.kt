package com.anmolsahi.domain.delegate

import com.anmolsahi.domain.models.RedditPost

interface HomeDelegate {
    suspend fun updateSavedPosts(shouldSavePost: Boolean, post: RedditPost?)
}
