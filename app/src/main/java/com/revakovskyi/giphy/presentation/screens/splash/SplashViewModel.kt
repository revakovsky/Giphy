package com.revakovskyi.giphy.presentation.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revakovskyi.giphy.core.ConnectivityObserver
import com.revakovskyi.giphy.presentation.screens.splash.model.SplashEvent
import com.revakovskyi.giphy.presentation.screens.splash.model.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    var state by mutableStateOf(SplashState(hasInternetConnection = null))
        private set

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.CheckConnectivity -> checkConnectivity()
            SplashEvent.ResetState -> state = SplashState()
            SplashEvent.CloseApp -> state = state.copy(shouldCloseApp = true)
        }
    }

    private fun checkConnectivity() {
        if (!connectivityObserver.hasConnection()) state = state.copy(hasInternetConnection = false)
        connectivityObserver.observeConnectivity().onEach { connectivityStatus ->
            state = when (connectivityStatus) {
                ConnectivityObserver.Status.Available -> state.copy(hasInternetConnection = true)
                else -> state.copy(hasInternetConnection = false)
            }
        }.launchIn(viewModelScope)
    }

}
