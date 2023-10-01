package com.revakovskyi.giphy.presentation.screens.splash

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.revakovskyi.giphy.presentation.screens.splash.mvi.SplashEvent
import com.revakovskyi.giphy.presentation.screens.splash.mvi.SplashState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onOpenGifsScreen: () -> Unit,
    onEvent: (SplashEvent) -> Unit,
    state: SplashState,
) {
    val context = LocalContext.current

    var animationProgress by remember { mutableFloatStateOf(0f) }
    var hasConnection by remember { mutableStateOf<Boolean?>(null) }

    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ -> }

    LaunchedEffect(key1 = true) { onEvent(SplashEvent.CheckConnectivity) }

    LaunchedEffect(key1 = animationProgress, key2 = state.hasInternetConnection) {
        if (animationProgress == 1.0f && state.hasInternetConnection == true) onOpenGifsScreen()
    }

    LaunchedEffect(key1 = state) {
        if (state.shouldCloseApp) (context as Activity).finish()

        delay(200L)
        hasConnection = when (state.hasInternetConnection) {
            true -> true
            else -> false
        }
    }

    when (hasConnection) {
        true -> Greetings(onGreetingsProgressChange = { animationProgress = it })
        false -> {
            NoInternetConnection(
                onOpenSettings = { settingsLauncher.launch(Intent(Settings.ACTION_WIFI_SETTINGS)) }
            )
        }
        else -> Unit
    }

    BackHandler { onEvent(SplashEvent.CloseApp) }

    DisposableEffect(Unit) {
        onDispose { onEvent(SplashEvent.ResetState) }
    }

}
