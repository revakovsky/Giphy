package com.revakovskyi.giphy.presentation.screens.splash.mvi

sealed class SplashEvent {

    object CheckConnectivity : SplashEvent()
    object ResetState : SplashEvent()
    object CloseApp : SplashEvent()

}
