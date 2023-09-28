package com.revakovskyi.giphy.presentation.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revakovskyi.giphy.core.ConnectivityObserver
import com.revakovskyi.giphy.presentation.screens.splash.mvi.SplashEvent
import com.revakovskyi.giphy.presentation.screens.splash.mvi.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    var state by mutableStateOf(SplashState())
        private set

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.CheckConnectivity -> checkConnectivity()
            SplashEvent.ResetState -> state = SplashState()
        }
    }

    private fun checkConnectivity() {
        viewModelScope.launch {
            connectivityObserver.observeConnectivity().collectLatest { status ->
                state =
                    if (status == ConnectivityObserver.Status.Available) {
                        state.copy(hasInternetConnection = true)
                    } else state.copy(hasInternetConnection = false)
            }
        }
    }

}
