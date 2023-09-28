package com.revakovskyi.giphy.presentation.screens.gif_info

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.presentation.ui.theme.dimens
import com.revakovskyi.giphy.presentation.widgets.ToolBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifInfoScreen(
    url: String,
    onBackToPreviousScreen: () -> Unit,
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) add(ImageDecoderDecoder.Factory())
            else add(GifDecoder.Factory())
        }
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .build()

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

            Image(
                contentDescription = null,
                modifier = Modifier
                    .padding(MaterialTheme.dimens.small)
                    .aspectRatio(1.0f)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .build(),
                    imageLoader = imageLoader
                )
            )

        }

    }

}
