package com.revakovskyi.data.di

import android.content.Context
import androidx.room.Room
import com.revakovskyi.data.local.GifDataBase
import com.revakovskyi.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataModuleProvider {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiService.provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(@ApplicationContext context: Context): GifDataBase {
        return Room.databaseBuilder(
            context,
            GifDataBase::class.java,
            "GIF_DB.db"
        ).build()
    }

}