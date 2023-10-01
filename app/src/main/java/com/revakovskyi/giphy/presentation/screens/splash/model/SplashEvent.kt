package com.revakovskyi.giphy.presentation.screens.splash.model

sealed class SplashEvent {

    object CheckConnectivity : SplashEvent()
    object ResetState : SplashEvent()
    object CloseApp : SplashEvent()

}
