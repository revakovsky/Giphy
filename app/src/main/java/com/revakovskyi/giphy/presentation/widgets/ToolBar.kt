package com.revakovskyi.giphy.presentation.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.presentation.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int? = null,
    showNavigationIcon: Boolean = true,
    navigationIcon: ImageVector = Icons.Default.KeyboardArrowLeft,
    onNavigationIconClick: () -> Unit = { },
    scrollBehaviour: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
) {

    TopAppBar(
        modifier = modifier.background(
            Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    MaterialTheme.colorScheme.tertiary,
                )
            )
        ),
        title = {
            if (titleRes != null) TextTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = MaterialTheme.dimens.medium),
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            if (showNavigationIcon) IconButton(onClick = { onNavigationIconClick() }) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        scrollBehavior = scrollBehaviour
    )

}