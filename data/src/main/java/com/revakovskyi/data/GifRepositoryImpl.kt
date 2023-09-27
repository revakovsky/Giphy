package com.revakovskyi.data

import com.revakovskyi.data.local.GifDataBase
import com.revakovskyi.data.remote.ApiService
import com.revakovskyi.domain.models.Gif
import com.revakovskyi.domain.repository.GifRepository
import javax.inject.Inject

internal class GifRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gifDataBase: GifDataBase
) : GifRepository {

    val dao = gifDataBase.dao

    override suspend fun provideTrendingGifs(): List<Gif> {
        return emptyList()  // TODO
    }

    override suspend fun provideSearchedGifs(query: String): List<Gif> {
        return emptyList()  // TODO
    }

}