package com.revakovskyi.data

import com.revakovskyi.data.local.GifDataBase
import com.revakovskyi.data.remote.ApiService
import com.revakovskyi.data.remote.dto.Data
import com.revakovskyi.data.utils.ExceptionHandler
import com.revakovskyi.data.utils.mapToGifEntity
import com.revakovskyi.domain.repository.GifRepository
import com.revakovskyi.domain.util.DataResult
import javax.inject.Inject

internal class GifRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    gifDataBase: GifDataBase,
    private val exceptionHandler: ExceptionHandler,
) : GifRepository {

    private val dao = gifDataBase.dao

    override suspend fun provideTrendingGifsUrls(shouldRefreshGifs: Boolean): DataResult<List<String>> {
        return try {
            if (shouldRefreshGifs) downloadNewGifs()
            else checkGifsInLocalDb()
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private suspend fun checkGifsInLocalDb(): DataResult<List<String>> {
        val localGifs = dao.provideGifEntities()

        return if (localGifs.isNotEmpty()) {
            val gifsUrls = localGifs.map { it.url }
            DataResult.Success(gifsUrls)
        } else downloadNewGifs()
    }

    private suspend fun downloadNewGifs(): DataResult<List<String>> {
        return try {
            val trendingGifs = apiService.getTrendingGifs()
            processRemoteData(trendingGifs.data)
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

    private suspend fun processRemoteData(remoteData: List<Data>): DataResult.Success<List<String>> {
        val gifEntities = remoteData.map { it.mapToGifEntity() }

        dao.apply {
            clearLocalDb()
            insertGifEntities(gifEntities)
        }
        val gifsUrls = dao.provideGifEntities().map { it.url }
        return DataResult.Success(gifsUrls)
    }

    override suspend fun provideSearchedGifsUrls(query: String): DataResult<List<String>> {
        return try {
            val searchedGifs = apiService.getGifsByQuery(query = query)
            val gifsUrls = searchedGifs.data.map { it.images.original.url }
            DataResult.Success(gifsUrls)
        } catch (e: Exception) {
            DataResult.Error(message = exceptionHandler.handleException(e))
        }
    }

}