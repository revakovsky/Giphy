package com.revakovskyi.giphy.presentation.screens.splash.mvi

internal sealed class SplashEvent {

    object CheckConnectivity : SplashEvent()
    object ResetState : SplashEvent()

}