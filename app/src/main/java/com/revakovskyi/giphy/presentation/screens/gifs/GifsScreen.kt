package com.revakovskyi.giphy.presentation.screens.gifs

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.presentation.screens.gifs.model.GifsEvent
import com.revakovskyi.giphy.presentation.screens.gifs.model.GifsState
import com.revakovskyi.giphy.presentation.ui.theme.dimens
import com.revakovskyi.giphy.presentation.widgets.CoilImage
import com.revakovskyi.giphy.presentation.widgets.OutlinedField
import com.revakovskyi.giphy.presentation.widgets.ToolBar

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
)
@Composable
fun GifsScreen(
    onOpenGifInfoScreen: (String) -> Unit,
    onEvent: (GifsEvent) -> Unit,
    state: GifsState,
    imageLoader: ImageLoader,
) {

    val context = LocalContext.current
    val orientation by remember { mutableIntStateOf(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) }

    val snackbarHostState = remember { SnackbarHostState() }
    val swipeRefreshState = rememberSwipeRefreshState(state.isLoading)

    LaunchedEffect(key1 = true) {
        (context as Activity).requestedOrientation = orientation
    }

    LaunchedEffect(key1 = state) {
        if (state.errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(state.errorMessage)
            onEvent(GifsEvent.ResetState)
        }
        if (state.gifsUrls?.isEmpty() == true) {
            snackbarHostState.showSnackbar(context.getString(R.string.nothing_was_found))
        }
        if (state.chosenGifUrl.isNotEmpty()) onOpenGifInfoScreen(state.chosenGifUrl)
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
                        items(state.gifsUrls?.size ?: 0) { itemIndex ->
                            if (state.gifsUrls != null) {
                                val url = state.gifsUrls[itemIndex]

                                CoilImage(
                                    imageLoader = imageLoader,
                                    url = url,
                                    onImageClick = { chosenGifUrl ->
                                        onEvent(GifsEvent.OnGifClick(chosenGifUrl))
                                    },
                                    clickable = true
                                )

                            }

                        }
                    }
                )

            }

        }

    }

    BackHandler { onEvent(GifsEvent.OnBackButtonPressed) }

    DisposableEffect(Unit) {
        onDispose { onEvent(GifsEvent.ResetChosenGifUrl) }
    }

}
