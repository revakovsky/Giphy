package com.revakovskyi.domain.repository

import com.revakovskyi.domain.models.Gif

interface GifRepository {

    suspend fun provideTrendingGifs(): List<Gif>
    suspend fun provideSearchedGifs(query: String): List<Gif>

}