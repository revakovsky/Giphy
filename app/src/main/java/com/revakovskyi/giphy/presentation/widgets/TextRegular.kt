package com.revakovskyi.giphy.presentation.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextRegular(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign = TextAlign.Center,
) {

    Text(
        modifier = modifier,
        text = text,
        style = style.copy(textAlign = textAlign),
        color = MaterialTheme.colorScheme.onBackground,
    )

}