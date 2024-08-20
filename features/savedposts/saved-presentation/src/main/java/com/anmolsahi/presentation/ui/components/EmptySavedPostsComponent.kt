package com.anmolsahi.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anmolsahi.designsystem.theme.BRTheme
import com.anmolsahi.subredditpresentation.R

@Composable
internal fun EmptySavedPostsComponent(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .padding(top = 48.dp),
            painter = painterResource(R.drawable.empty),
            contentDescription = null,
        )

        Text(
            modifier = Modifier
                .padding(top = 16.dp),
            text = stringResource(R.string.no_posts_title),
            style = BRTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Text(
            text = stringResource(R.string.no_posts_body),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.90f),
            style = BRTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}
