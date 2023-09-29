package com.revakovskyi.giphy.presentation.screens.gif_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.presentation.widgets.CoilImage
import com.revakovskyi.giphy.presentation.widgets.ToolBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifInfoScreen(
    url: String,
    onBackToPreviousScreen: () -> Unit,
    imageLoader: ImageLoader,
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        if (url.isEmpty()) snackbarHostState.showSnackbar(context.getString(R.string.failed_to_load_link))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { ToolBar(onNavigationIconClick = onBackToPreviousScreen) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            CoilImage(imageLoader = imageLoader, url = url)

        }

    }

}
