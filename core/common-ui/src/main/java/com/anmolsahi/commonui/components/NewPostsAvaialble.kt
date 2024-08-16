package com.anmolsahi.commonui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anmolsahi.commonui.R
import com.anmolsahi.designsystem.theme.BRTheme

@Composable
fun NewPostsAvailableComponent(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .background(
                    color = BRTheme.colorScheme.primary,
                    shape = CircleShape,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp, bottom = 4.dp),
                text = stringResource(R.string.new_posts_available),
                color = BRTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
            )

            Icon(
                modifier = Modifier
                    .padding(start = 4.dp, end = 16.dp)
                    .size(18.dp),
                imageVector = Icons.Default.ArrowUpward,
                tint = BRTheme.colorScheme.onPrimary,
                contentDescription = stringResource(R.string.click_to_refresh_and_get_new_posts),
            )
        }
    }
}

@SuppressWarnings("UnusedPrivateMember")
@Preview
@Composable
private fun NewPostsPreview() {
    NewPostsAvailableComponent {}
}
