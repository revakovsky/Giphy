package com.revakovskyi.data.di

import com.revakovskyi.data.GifRepositoryImpl
import com.revakovskyi.data.utils.ExceptionHandler
import com.revakovskyi.data.utils.ExceptionHandlerImpl
import com.revakovskyi.domain.repository.GifRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModuleBinder {

    @Binds
    @Singleton
    abstract fun bindGifRepository(gifRepositoryImpl: GifRepositoryImpl): GifRepository

    @Binds
    @Singleton
    abstract fun bindExceptionHandler(exceptionHandlerImpl: ExceptionHandlerImpl): ExceptionHandler

}