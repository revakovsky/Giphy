package com.revakovskyi.giphy.presentation.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.revakovskyi.giphy.presentation.ui.theme.dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ButtonRegular(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundGradient: Brush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.tertiary,
        )
    ),
    bringIntoViewRequester: BringIntoViewRequester = BringIntoViewRequester(),
) {

    Button(
        onClick = { onClick() },
        modifier = modifier
            .width(MaterialTheme.dimens.buttonWidth)
            .bringIntoViewRequester(bringIntoViewRequester)
            .padding(top = MaterialTheme.dimens.large),
        contentPadding = PaddingValues(),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = MaterialTheme.dimens.zero,
            pressedElevation = MaterialTheme.dimens.smallest
        ),
        enabled = enabled
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundGradient)
                .padding(
                    horizontal = MaterialTheme.dimens.medium,
                    vertical = MaterialTheme.dimens.small
                ),
            contentAlignment = Alignment.Center
        ) {

            TextRegular(
                text = buttonText,
                style = MaterialTheme.typography.displayMedium
            )

        }

    }
}