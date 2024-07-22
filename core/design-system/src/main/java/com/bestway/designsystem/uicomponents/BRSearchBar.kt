package com.bestway.designsystem.uicomponents

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bestway.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BRSearchBar(
    query: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit = {},
    onBack: () -> Unit = {},
    onActiveChange: (active: Boolean) -> Unit,
    onQueryChange: (newValue: String) -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchBarPadding by animateDpAsState(
        targetValue = if (!active) 16.dp else 0.dp,
        label = "searchBarPadding",
    )

    SearchBar(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = searchBarPadding),
        query = query,
        onQueryChange = { newValue ->
            onQueryChange(newValue)
        },
        onSearch = {
            onSearch()
            keyboardController?.hide()
            onActiveChange(false)
        },
        active = active,
        onActiveChange = onActiveChange,
        placeholder = { Text(stringResource(R.string.search_reddit)) },
        leadingIcon = {
            BRSearchLeadingIcon(
                active = active,
                onIconButtonClick = {
                    onBack()
                    onActiveChange(false)
                },
            )
        },
        trailingIcon = {
            BRSearchTrailingIcon(
                active = active,
                onIconButtonClick = {
                    if (query.isNotEmpty()) {
                        onQueryChange("")
                    } else {
                        onActiveChange(false)
                    }
                },
            )
        },
        content = content,
    )
}

@Composable
internal fun BRSearchLeadingIcon(
    active: Boolean,
    modifier: Modifier = Modifier,
    onIconButtonClick: () -> Unit,
) {
    if (!active) {
        Icon(
            modifier = modifier,
            imageVector = Icons.Default.Search,
            contentDescription = "Click here to search",
        )
    } else {
        IconButton(onClick = onIconButtonClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Click to go back",
            )
        }
    }
}

@Composable
internal fun BRSearchTrailingIcon(
    active: Boolean,
    modifier: Modifier = Modifier,
    onIconButtonClick: () -> Unit,
) {
    if (active) {
        IconButton(onClick = onIconButtonClick) {
            Icon(
                modifier = modifier,
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.click_to_delete),
            )
        }
    }
}

@Deprecated("")
@Composable
fun BRSearchBar(
    modifier: Modifier = Modifier,
    hintText: String,
    value: String,
    onSearch: (value: String) -> Unit,
    onValueChanged: (newValue: String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions =
            KeyboardActions {
                onSearch(value)
                keyboardController?.hide()
            },
        label = { Text(text = hintText) },
        trailingIcon = {
            IconButton(onClick = { if (value.isNotEmpty()) onValueChanged("") }) {
                Icon(
                    modifier = Modifier,
                    imageVector = if (value.isEmpty()) Icons.Default.Search else Icons.Default.Clear,
                    contentDescription = "Click here to search",
                )
            }
        },
    )
}
