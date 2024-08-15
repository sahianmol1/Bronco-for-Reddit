package com.anmolsahi.commonui.components.postactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anmolsahi.commonui.R
import com.anmolsahi.commonui.utils.dpToSp

@Composable
fun PostActionItem(
    icon: ImageVector,
    label: String,
    actionDescription: String,
    modifier: Modifier = Modifier,
    onclick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .clickable { onclick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 8.dp)
                .padding(vertical = 8.dp),
            imageVector = icon,
            contentDescription = stringResource(
                R.string.post_action_content_description,
                actionDescription,
            ),
        )
        Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            text = label,
            fontSize = dpToSp(dp = 12.dp),
            textAlign = TextAlign.Center,
        )
    }
}
