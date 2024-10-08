package com.anmolsahi.designsystem.utils

import com.anmolsahi.designsystem.utils.Routes.ABOUT_SCREEN_ROUTE
import com.anmolsahi.designsystem.utils.Routes.FULL_SIZE_IMAGE_ROUTE
import com.anmolsahi.designsystem.utils.Routes.HOME_SCREEN_ROUTE
import com.anmolsahi.designsystem.utils.Routes.POST_DETAILS_ROUTE
import com.anmolsahi.designsystem.utils.Routes.SAVED_SCREEN_ROUTE
import com.anmolsahi.designsystem.utils.Routes.SEARCH_SCREEN_ROUTE
import com.anmolsahi.designsystem.utils.Routes.VIDEO_PLAYER_ROUTE

sealed class Destinations(val route: String) {
    data object HomeScreenDestination : Destinations(route = HOME_SCREEN_ROUTE)

    data object SearchScreenDestination : Destinations(route = SEARCH_SCREEN_ROUTE)

    data object SavedScreenDestination : Destinations(route = SAVED_SCREEN_ROUTE)

    data object AboutUsDestination : Destinations(route = ABOUT_SCREEN_ROUTE)

    data object PostDetailsDestinations : Destinations(route = POST_DETAILS_ROUTE)

    data object VideoPlayerDestination : Destinations(route = VIDEO_PLAYER_ROUTE)

    data object FullSizeImageDestination : Destinations(route = FULL_SIZE_IMAGE_ROUTE)
}

object Routes {
    const val HOME_SCREEN_ROUTE = "home_screen"
    const val SEARCH_SCREEN_ROUTE = "search_screen"
    const val SAVED_SCREEN_ROUTE = "saved_screen"
    const val ABOUT_SCREEN_ROUTE = "about_us"
    const val POST_DETAILS_ROUTE = "post_details_route"
    const val VIDEO_PLAYER_ROUTE = "video_player_route"
    const val FULL_SIZE_IMAGE_ROUTE = "full_size_image_route"
}
