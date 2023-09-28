package com.revakovskyi.giphy.presentation.screens.gifs

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build.VERSION.SDK_INT
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.presentation.screens.gifs.mvi.GifsEvent
import com.revakovskyi.giphy.presentation.screens.gifs.mvi.GifsState
import com.revakovskyi.giphy.presentation.ui.theme.dimens
import com.revakovskyi.giphy.presentation.widgets.OutlinedField
import com.revakovskyi.giphy.presentation.widgets.ToolBar
import kotlinx.coroutines.delay
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
)
@Composable
fun GifsScreen(
    onOpenGifInfoScreen: (String) -> Unit,
    onEvent: (GifsEvent) -> Unit,
    state: GifsState,
) {

    val context = LocalContext.current
    val orientation by remember { mutableIntStateOf(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) }

    val snackbarHostState = remember { SnackbarHostState() }
    val swipeRefreshState = rememberSwipeRefreshState(state.isLoading)

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) add(ImageDecoderDecoder.Factory())
            else add(GifDecoder.Factory())
        }
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .build()

    LaunchedEffect(key1 = true) {
        (context as Activity).requestedOrientation = orientation
    }

    LaunchedEffect(key1 = state) {
        if (state.errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(state.errorMessage)
            onEvent(GifsEvent.ResetState)
        }
        if (state.gifs.isEmpty()) {
            delay(1000L)
            snackbarHostState.showSnackbar(context.getString(R.string.nothing_was_found))
        }
        if (state.shouldCloseTheApp) (context as Activity).finish()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            ToolBar(
                titleRes = R.string.app_name,
                showNavigationIcon = false
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedField(
                value = state.enteredQuery,
                status = state.queryVerificationStatus,
                onValueChange = { inputQuery -> onEvent(GifsEvent.ProvideGifsByQuery(inputQuery)) }
            )

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { onEvent(GifsEvent.RefreshGifs) },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            ) {

                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MaterialTheme.dimens.medium),
                    columns = GridCells.Adaptive(MaterialTheme.dimens.gifMinSize),
                    content = {
                        items(state.gifs.size) { itemIndex ->
                            val gif = state.gifs[itemIndex]

                            Image(
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(MaterialTheme.dimens.small)
                                    .aspectRatio(1.0f)
                                    .clip(MaterialTheme.shapes.small)
                                    .clickable {
                                        onOpenGifInfoScreen(
                                            URLEncoder.encode(gif.url, StandardCharsets.UTF_8.toString())
                                        )
                                    },
                                contentScale = ContentScale.Crop,
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(gif.url)
                                        .build(),
                                    imageLoader = imageLoader
                                )
                            )

                        }
                    }
                )

            }

        }

    }

    BackHandler { onEvent(GifsEvent.OnBackButtonPressed) }

}
