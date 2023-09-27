package com.revakovskyi.giphy.di

import com.revakovskyi.giphy.core.ConnectivityObserver
import com.revakovskyi.giphy.core.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MainModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver): ConnectivityObserver

}