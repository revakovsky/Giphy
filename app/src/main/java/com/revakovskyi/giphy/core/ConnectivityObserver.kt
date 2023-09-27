package com.revakovskyi.giphy.core

import kotlinx.coroutines.flow.Flow

internal interface ConnectivityObserver {

    fun observeConnectivity(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }

}