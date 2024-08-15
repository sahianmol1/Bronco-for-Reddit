package com.anmolsahi.postdetailspresentation.postdetails.ui.fullsizeimage

import android.content.res.Configuration
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.anmolsahi.commonui.components.PostImage
import com.anmolsahi.commonui.components.postimage.PostImagePager

@Composable
fun FullSizeImageScreen(
    modifier: Modifier = Modifier,
    imageList: List<String>,
    onExitIconClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val imageModifier = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        modifier
            .navigationBarsPadding()
            .statusBarsPadding()
    } else {
        modifier
    }

    if (imageList.size == 1) {
        PostImage(
            modifier = imageModifier,
            imageUrl = imageList.first(),
        )
    } else {
        PostImagePager(
            modifier = imageModifier,
            imageUrlList = imageList.toList(),
            showFullSizeImage = true,
            onExitIconClick = onExitIconClick,
        )
    }
}
