package com.revakovskyi.giphy.presentation.screens.splash

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.revakovskyi.giphy.presentation.screens.splash.mvi.SplashEvent
import com.revakovskyi.giphy.presentation.screens.splash.mvi.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(SplashState())
        private set

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.CheckConnectivity -> checkConnectivity(event.connectivityManager)
            SplashEvent.ResetState -> state = SplashState()
        }
    }

    private fun checkConnectivity(connectivityManager: ConnectivityManager) {
        connectivityManager.apply {
            this.getNetworkCapabilities(this.activeNetwork)?.let {
                val hasConnection = it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                state = state.copy(hasInternetConnection = hasConnection)
            } ?: run {
                state = state.copy(hasInternetConnection = false)
            }
        }
    }

}
