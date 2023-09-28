package com.revakovskyi.data

import android.content.Context
import com.revakovskyi.data.local.GifDataBase
import com.revakovskyi.data.mapper.mapToGif
import com.revakovskyi.data.mapper.mapToGifEntity
import com.revakovskyi.data.remote.ApiService
import com.revakovskyi.domain.models.Gif
import com.revakovskyi.domain.repository.GifRepository
import com.revakovskyi.domain.util.DataResult
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

internal class GifRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    gifDataBase: GifDataBase,
) : GifRepository {

    private val dao = gifDataBase.dao

    override suspend fun provideTrendingGifs(shouldRefreshGifs: Boolean): DataResult<List<Gif>> {
        return try {
            if (shouldRefreshGifs) downloadNewGifs()
            else {
                val localGifs = dao.provideGifEntities()

                if (localGifs.isNotEmpty()) {
                    val gifs = localGifs.map { it.mapToGif() }
                    DataResult.Success(gifs)
                } else downloadNewGifs()
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private suspend fun downloadNewGifs(): DataResult<List<Gif>> {
        return try {
            val trendingGifs = apiService.getTrendingGifs()
            val gifEntities = trendingGifs.data.map { it.mapToGifEntity() }

            dao.apply {
                clearLocalDb()
                insertGifEntities(gifEntities)
            }
            val gifs = dao.provideGifEntities().map { it.mapToGif() }
            DataResult.Success(gifs)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun provideSearchedGifs(query: String): DataResult<List<Gif>> {
        return try {
            val searchedGifs = apiService.getGifsByQuery(query = query)
            val gifEntities = searchedGifs.data.map { it.mapToGifEntity() }

            dao.apply {
                clearLocalDb()
                insertGifEntities(gifEntities)
            }
            val gifs = dao.provideGifEntities().map { it.mapToGif() }
            DataResult.Success(gifs)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun handleException(e: Exception): DataResult.Error<List<Gif>> {
        e.printStackTrace()
        return when (e) {
            is HttpException -> DataResult.Error(
                data = null,
                message = context.getString(R.string.could_not_load_from_the_server)
            )
            is CancellationException -> throw e
            else -> DataResult.Error(
                data = null,
                message = context.getString(R.string.something_went_wrong)
            )
        }
    }

}