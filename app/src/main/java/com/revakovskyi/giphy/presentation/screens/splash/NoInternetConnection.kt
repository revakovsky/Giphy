package com.revakovskyi.giphy.presentation.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.presentation.ui.theme.dimens
import com.revakovskyi.giphy.presentation.widgets.ButtonRegular
import com.revakovskyi.giphy.presentation.widgets.TextRegular
import com.revakovskyi.giphy.presentation.widgets.TextTitle

@Composable
fun NoInternetConnection(
    modifier: Modifier = Modifier,
    onOpenSettings: () -> Unit,
) {

    val noConnectionAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_connection))

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            LottieAnimation(
                modifier = Modifier.size(MaterialTheme.dimens.lottieAnimationsSize),
                composition = noConnectionAnimation,
                iterations = LottieConstants.IterateForever
            )

            TextTitle(
                modifier = Modifier.padding(top = MaterialTheme.dimens.large),
                text = stringResource(R.string.oops)
            )

            TextRegular(
                modifier = Modifier.padding(top = MaterialTheme.dimens.large),
                text = stringResource(R.string.connection_was_lost),
            )

            ButtonRegular(
                modifier = Modifier.padding(top = MaterialTheme.dimens.large),
                buttonText = stringResource(R.string.open_settings),
                onClick = onOpenSettings
            )
        }

    }

}
