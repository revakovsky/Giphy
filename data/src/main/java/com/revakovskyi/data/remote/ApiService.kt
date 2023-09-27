package com.revakovskyi.data.remote

import com.revakovskyi.data.remote.dto.SearchGifsDto
import com.revakovskyi.data.remote.dto.TrendingGifsDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {

    @GET("trending?limit=25&offset=0&rating=g")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String = API_KEY,
    ): TrendingGifsDto

    @GET("search?limit=25&offset=0&rating=g&lang=en")
    suspend fun getGifsByQuery(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("q") query: String,
    ): SearchGifsDto


    companion object {
        private const val BASE_URL = "https://api.giphy.com/v1/gifs/"
        private const val API_KEY = "3FQrv9Cok9ki6pwej7g8G0l0LyJqQwV9"

        fun provideBaseUrl() = BASE_URL
    }

}