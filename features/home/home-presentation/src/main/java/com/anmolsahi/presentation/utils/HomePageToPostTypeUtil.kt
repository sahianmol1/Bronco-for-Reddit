package com.anmolsahi.presentation.utils

import com.anmolsahi.designsystem.uicomponents.HomePage
import com.anmolsahi.domain.utils.PostType

fun HomePage.asPostType(): PostType {
    return when (this) {
        HomePage.HOT -> PostType.HOT
        HomePage.NEW -> PostType.NEW
        HomePage.TOP -> PostType.TOP
        HomePage.BEST -> PostType.BEST
        HomePage.RISING -> PostType.RISING
        HomePage.CONTROVERSIAL -> PostType.CONTROVERSIAL
    }
}
