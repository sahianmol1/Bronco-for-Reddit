package com.anmolsahi.commonui.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.anmolsahi.commonui.R

@Composable
fun DeleteSavedPostAlertDialog(
    modifier: Modifier = Modifier,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissButtonClick,
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(
                    text = stringResource(id = R.string.delete),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissButtonClick) {
                Text(text = stringResource(R.string.dismiss))
            }
        },
        title = {
            Text(
                text = stringResource(R.string.delete_post_dialog_title_text),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error,
            )
        },
        text = {
            Text(text = stringResource(R.string.delete_post_dialog_info_text))
        },
    )
}

@Composable
fun ErrorDialog(
    errorMessage: String,
    modifier: Modifier = Modifier,
    onConfirmButtonClick: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onConfirmButtonClick,
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(
                    text = stringResource(id = R.string.error_dialog_button_text),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.error_dialog_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error,
            )
        },
        text = {
            Text(text = errorMessage)
        },
    )
}
