package com.anmolsahi.postdetailspresentation.postdetails.ui.fullsizeimage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.anmolsahi.commonui.components.PostImage
import com.anmolsahi.commonui.components.postimage.PostImagePager

@Composable
fun FullSizeImageScreen(modifier: Modifier = Modifier, imageList: Array<out String>) {
    if (imageList.size == 1) {
        PostImage(
            modifier = modifier,
            imageUrl = imageList[0],
            onImageClick = {},
        )
    } else {
        PostImagePager(
            modifier = modifier,
            imageUrlList = imageList.toList(),
            showFullSizeImage = true,
            onImageClick = {},
        )
    }
}
