package com.anmolsahi.commonui.components.postimage

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.anmolsahi.commonui.components.PostImage
import com.anmolsahi.designsystem.theme.BRTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostImagePager(
    imageUrlList: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: () -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { imageUrlList.size },
    )

    Column {
        Box(
            modifier = modifier
                .animateContentSize(
                    animationSpec = tween(300, easing = LinearEasing, delayMillis = 500),
                )
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopEnd,
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                state = pagerState,
            ) { index ->
                Box(modifier = Modifier.wrapContentSize()) {
                    PostImage(
                        modifier = Modifier
                            .animateContentSize()
                            .zIndex(2f),
                        imageUrl = imageUrlList[index],
                        onImageClick = onImageClick,
                    )
                }
            }

            Text(
                modifier = Modifier
                    .zIndex(1f)
                    .padding(16.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.7f),
                        shape = CircleShape,
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = "${pagerState.currentPage + 1}/${pagerState.pageCount}",
                color = Color.White,
                style = TextStyle(),
            )
        }

        PagerIndicator(
            modifier = Modifier.padding(top = 4.dp),
            count = pagerState.pageCount,
            currentPage = pagerState.currentPage,
        )
    }
}

@Composable
fun PagerIndicator(modifier: Modifier = Modifier, count: Int, currentPage: Int) {
    val indicatorColorSelected = BRTheme.colorScheme.primary
    val indicatorColorUnselected = BRTheme.colorScheme.inverseSurface

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(count) { index ->
            val indicatorSize by animateDpAsState(
                targetValue = if (currentPage == index) 8.dp else 6.dp,
                label = "indicator size",
                animationSpec = tween(200, easing = LinearEasing),
            )
            Canvas(
                modifier = modifier
                    .padding(horizontal = 4.dp)
                    .size(indicatorSize),
            ) {
                drawCircle(
                    color = if (currentPage == index) {
                        indicatorColorSelected
                    } else {
                        indicatorColorUnselected
                    },
                )
            }
        }
    }
}
