package com.anmolsahi.presentation.ui

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anmolsahi.aboutpresentation.R
import com.anmolsahi.commonui.chrometabs.openAsCustomTab
import com.anmolsahi.designsystem.theme.BRTheme

@Composable
fun AboutUsScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val currentAppVersion = context.currentAppVersion

    val topLevelModifier = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> modifier.navigationBarsPadding()
        else -> modifier
    }

    Column(
        modifier = topLevelModifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .padding(top = 72.dp)
                .size(124.dp),
            painter = painterResource(id = R.drawable.ic_bronco),
            contentDescription = stringResource(
                R.string.app_icon,
            ),
        )

        Text(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp),
            text = context.applicationInfo.loadLabel(context.packageManager).toString(),
            textAlign = TextAlign.Center,
            style = BRTheme.typography.headlineMedium,
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.app_description),
            textAlign = TextAlign.Center,
            style = BRTheme.typography.bodyMedium,
        )

        InfoIconButton(
            modifier = Modifier
                .padding(top = 16.dp),
            text = "Source Code",
            icon = Icons.Outlined.Code,
            onClick = {
                "https://github.com/sahianmol1/Bronco-for-Reddit".openAsCustomTab(context)
            },
        )

        InfoIconButton(
            text = "anmolsahi1",
            icon = R.drawable.github_logo,
            onClick = {
                "https://github.com/sahianmol1".openAsCustomTab(context)
            },
        )

        InfoIconButton(
            text = "App Icon source",
            icon = Icons.Outlined.Language,
            onClick = {
                "https://www.svgrepo.com/svg/34625/trot-horse-outline".openAsCustomTab(context)
            },
        )

        Spacer(modifier = Modifier.weight(1f))

        VersionFooter(currentAppVersion = currentAppVersion)
    }
}

@Composable
private fun InfoIconButton(
    text: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),

        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(18.dp),
                painter = painterResource(id = icon),
                contentDescription = text,
            )

            Text(text = text)
        }
    }
}

@Composable
private fun InfoIconButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),

        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Row {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(18.dp),
                imageVector = icon,
                contentDescription = text,
            )

            Text(text = text)
        }
    }
}

@Composable
fun VersionFooter(currentAppVersion: String) {
    val context = LocalContext.current
    Text(
        text = "${stringResource(id = R.string.package_name)} - ${context.packageName}",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        style = BRTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        color = BRTheme.colorScheme.primary,
    )
    Text(
        text = "${stringResource(id = R.string.app_version)} - $currentAppVersion",
        modifier = Modifier.fillMaxWidth(),
        style = BRTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        color = BRTheme.colorScheme.primary,
    )
}

private val Context.currentAppVersion: String
    get() = packageManager.getPackageInfo(packageName, 0).versionName ?: ""

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_MASK)
@Composable
private fun AboutUsScreenPreview() {
    BRTheme {
        AboutUsScreen()
    }
}
