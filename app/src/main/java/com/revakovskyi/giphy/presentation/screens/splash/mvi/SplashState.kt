package com.revakovskyi.giphy.presentation.screens.splash.mvi

data class SplashState(
    val hasInternetConnection: Boolean? = null,
    val shouldCloseApp: Boolean = false,
)
