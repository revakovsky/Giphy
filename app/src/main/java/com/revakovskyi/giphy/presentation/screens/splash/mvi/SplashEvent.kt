package com.revakovskyi.giphy.presentation.screens.splash.mvi

import android.net.ConnectivityManager

internal sealed class SplashEvent {

    data class CheckConnectivity(val connectivityManager: ConnectivityManager) : SplashEvent()
    object ResetState : SplashEvent()

}