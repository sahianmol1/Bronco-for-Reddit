package com.bestway.broncoforreddit.ui.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PostWidget(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .clickable {  }
      .padding(16.dp),
  ) {
    SubRedditName()
    PostTitle()
    PostDescription()
  }
}

@Composable
fun SubRedditName() {
  Text(
    modifier = Modifier.padding(bottom = 8.dp),
    style = TextStyle(
      fontWeight = FontWeight.Bold
    ),
    text = "r/onexindia",
    maxLines = 1,
    overflow = TextOverflow.Ellipsis
  )
}

@Composable
fun PostTitle() {
  Text(
    modifier = Modifier.padding(vertical = 4.dp),
        fontWeight = FontWeight.Bold,
    text = "How rich do I have to be to stop caring if my partner is smart in money matters?",
    maxLines = 3,
    overflow = TextOverflow.Ellipsis
  )
}

@Composable
fun PostDescription() {
  Text(
    modifier = Modifier.padding(vertical = 4.dp),
    text =
      "I always worry that my partner is not smart enough and keep rejecting people based on this, so much that I have developed a bias that women should have good money understanding. How should I stop caring about this. Please help.",
    color = MaterialTheme.colorScheme.onSecondaryContainer,
    maxLines = 3,
    overflow = TextOverflow.Ellipsis
  )
}

@Preview
@Composable
fun PostPreview() {
  PostWidget()
}
