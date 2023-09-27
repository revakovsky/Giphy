package com.revakovskyi.data.remote

import com.revakovskyi.data.remote.dto.SearchGifsDto
import com.revakovskyi.data.remote.dto.TrendingGifsDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {

    @GET(TRENDING_END_POINT)
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String = API_KEY,
    ): TrendingGifsDto

    @GET(SEARCHED_END_POINT)
    suspend fun getGifsByQuery(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("q") query: String,
    ): SearchGifsDto


    companion object {
        private const val BASE_URL = "https://api.giphy.com/v1/gifs/"
        private const val API_KEY = "3FQrv9Cok9ki6pwej7g8G0l0LyJqQwV9"
        private const val TRENDING_END_POINT = "trending?limit=25&offset=0&rating=g"
        private const val SEARCHED_END_POINT = "search?limit=25&offset=0&rating=g&lang=en"

        fun provideBaseUrl() = BASE_URL
    }

}