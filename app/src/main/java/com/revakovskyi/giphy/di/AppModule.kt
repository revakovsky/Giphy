package com.revakovskyi.giphy.di

import com.revakovskyi.giphy.core.ConnectivityObserver
import com.revakovskyi.giphy.core.NetworkConnectivityObserver
import com.revakovskyi.giphy.core.QueryManager
import com.revakovskyi.giphy.core.QueryManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class AppModule {

    @Binds
    abstract fun bindConnectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver): ConnectivityObserver

    @Binds
    abstract fun bindQueryManager(queryManagerImpl: QueryManagerImpl): QueryManager

}