package com.revakovskyi.giphy.core

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observeConnectivity(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }

}