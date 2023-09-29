package com.revakovskyi.data

import com.revakovskyi.data.local.GifDataBase
import com.revakovskyi.data.remote.ApiService
import com.revakovskyi.data.remote.dto.Data
import com.revakovskyi.data.utils.ExceptionHandler
import com.revakovskyi.data.utils.mapToGif
import com.revakovskyi.data.utils.mapToGifEntity
import com.revakovskyi.domain.models.Gif
import com.revakovskyi.domain.repository.GifRepository
import com.revakovskyi.domain.util.DataResult
import javax.inject.Inject

internal class GifRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    gifDataBase: GifDataBase,
    private val exceptionHandler: ExceptionHandler,
) : GifRepository {

    private val dao = gifDataBase.dao

    override suspend fun provideTrendingGifs(shouldRefreshGifs: Boolean): DataResult<List<Gif>> {
        return try {
            if (shouldRefreshGifs) downloadNewGifs()
            else checkGifsInLocalDb()
        } catch (e: Exception) {
            DataResult.Error(
                data = null,
                message = exceptionHandler.handleException(e)
            )
        }
    }

    private suspend fun checkGifsInLocalDb(): DataResult<List<Gif>> {
        val localGifs = dao.provideGifEntities()

        return if (localGifs.isNotEmpty()) {
            val gifs = localGifs.map { it.mapToGif() }
            DataResult.Success(gifs)
        } else downloadNewGifs()
    }

    private suspend fun downloadNewGifs(): DataResult<List<Gif>> {
        return try {
            val trendingGifs = apiService.getTrendingGifs()
            processRemoteData(trendingGifs.data)
        } catch (e: Exception) {
            DataResult.Error(
                data = null,
                message = exceptionHandler.handleException(e)
            )
        }
    }

    private suspend fun processRemoteData(remoteData: List<Data>): DataResult.Success<List<Gif>> {
        val gifEntities = remoteData.map { it.mapToGifEntity() }

        dao.apply {
            clearLocalDb()
            insertGifEntities(gifEntities)
        }
        val gifs = dao.provideGifEntities().map { it.mapToGif() }
        return DataResult.Success(gifs)
    }

    override suspend fun provideSearchedGifs(query: String): DataResult<List<Gif>> {
        return try {
            val searchedGifs = apiService.getGifsByQuery(query = query)
            val gifs = searchedGifs.data.map { it.mapToGif() }
            DataResult.Success(gifs)
        } catch (e: Exception) {
            DataResult.Error(
                data = null,
                message = exceptionHandler.handleException(e)
            )
        }
    }

}