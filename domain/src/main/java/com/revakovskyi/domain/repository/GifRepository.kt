package com.revakovskyi.domain.repository

import com.revakovskyi.domain.util.DataResult

interface GifRepository {

    suspend fun provideTrendingGifsUrls(shouldRefreshGifs: Boolean): DataResult<List<String>>
    suspend fun provideSearchedGifsUrls(query: String): DataResult<List<String>>

}