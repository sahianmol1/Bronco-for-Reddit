package com.bestway.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var searchedValue by remember { mutableStateOf("") }
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            hintText = "Search Reddit",
            value = searchedValue,
            onValueChanged = { newValue -> searchedValue = newValue },
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hintText: String,
    value: String,
    onValueChanged: (newValue: String) -> Unit,
) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterEnd) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChanged,
            label = { Text(text = hintText) },
        )

        Icon(
            modifier = Modifier.padding(end = 8.dp, top = 6.dp),
            imageVector = if (value.isEmpty()) Icons.Default.Search else Icons.Default.Clear,
            contentDescription = "Click here to search",
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        hintText = "Search",
        value = "",
        onValueChanged = {},
    )
}
