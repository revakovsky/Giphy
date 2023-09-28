package com.revakovskyi.domain.repository

import com.revakovskyi.domain.models.Gif
import com.revakovskyi.domain.util.DataResult

interface GifRepository {

    suspend fun provideTrendingGifs(shouldRefreshGifs: Boolean): DataResult<List<Gif>>
    suspend fun provideSearchedGifs(query: String): DataResult<List<Gif>>

}