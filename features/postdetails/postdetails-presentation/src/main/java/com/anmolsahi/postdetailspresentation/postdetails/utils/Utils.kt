package com.anmolsahi.postdetailspresentation.postdetails.utils

import com.anmolsahi.commonui.models.RedditPostUiModel

internal fun shouldShowCommentsComponent(comment: RedditPostUiModel) =
    comment.author.isNotEmpty() && !comment.author.contains("mod", true)
