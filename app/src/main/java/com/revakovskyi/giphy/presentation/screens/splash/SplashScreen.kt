package com.revakovskyi.giphy.presentation.screens.splash

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.presentation.screens.splash.mvi.SplashEvent
import com.revakovskyi.giphy.presentation.screens.splash.mvi.SplashState
import com.revakovskyi.giphy.presentation.ui.theme.DevicePreviews
import com.revakovskyi.giphy.presentation.ui.theme.GiphyTheme
import com.revakovskyi.giphy.presentation.ui.theme.dimens

@Composable
internal fun SplashScreen(
    onOpenGifsScreen: () -> Unit,
    onEvent: (SplashEvent) -> Unit,
    state: SplashState,
) {
    val context = LocalContext.current

    val smailAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.smail))
    val greetingsAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.greetings))
    val noConnectionAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_connection))

    val greetingsProgress by animateLottieCompositionAsState(composition = greetingsAnimation)

    LaunchedEffect(key1 = true) {
        onEvent(
            SplashEvent.CheckConnectivity(
                connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            )
        )
    }

    if (greetingsProgress == 1.0f && state.hasInternetConnection == true) onOpenGifsScreen()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (state.hasInternetConnection == true) {

                LottieAnimation(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.lottieAnimationsSize)
                        .weight(1.0f),
                    composition = smailAnimation,
                )

                LottieAnimation(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.lottieAnimationsSize)
                        .weight(1.0f),
                    composition = greetingsAnimation,
                    progress = { greetingsProgress }
                )

            } else {

                LottieAnimation(
                    modifier = Modifier.size(MaterialTheme.dimens.lottieAnimationsSize),
                    composition = noConnectionAnimation,
                    iterations = LottieConstants.IterateForever
                )

            }

        }
    }

    BackHandler {
        (context as Activity).finish()
    }

    DisposableEffect(Unit) {
        onDispose { onEvent(SplashEvent.ResetState) }
    }

}

@Composable
@DevicePreviews
fun PreviewSplashScreen() {
    GiphyTheme {
        Surface {
            SplashScreen(
                onOpenGifsScreen = { },
                onEvent = { },
                state = SplashState()
            )
        }
    }
}
