package com.revakovskyi.giphy.presentation.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.presentation.ui.theme.dimens

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    onGreetingsProgressChange: (Float) -> Unit,
) {

    val smailAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.smail))
    val greetingsAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.greetings))
    val greetingsProgress by animateLottieCompositionAsState(composition = greetingsAnimation)

    LaunchedEffect(key1 = greetingsProgress) {
        onGreetingsProgressChange(greetingsProgress)
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = modifier.padding(vertical = MaterialTheme.dimens.large),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                LottieAnimation(
                    modifier = Modifier.size(MaterialTheme.dimens.lottieAnimationsSize),
                    composition = smailAnimation,
                )

                LottieAnimation(
                    modifier = Modifier.size(MaterialTheme.dimens.lottieAnimationsSize),
                    composition = greetingsAnimation,
                    progress = { greetingsProgress }
                )

            }

        }

    }

}
