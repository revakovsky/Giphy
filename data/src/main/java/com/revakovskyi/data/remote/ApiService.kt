package com.revakovskyi.data.remote

import com.revakovskyi.data.BuildConfig
import com.revakovskyi.data.remote.dto.GifsDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {

    @GET("trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("limit") limit: Int = LIMIT,
        @Query("offset") offset: Int = OFFSET,
        @Query("rating") rating: String = RATING,
    ): GifsDto

    @GET("search")
    suspend fun getGifsByQuery(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") query: String,
        @Query("limit") limit: Int = LIMIT,
        @Query("offset") offset: Int = OFFSET,
        @Query("rating") rating: String = RATING,
        @Query("lang") lang: String = LANGUAGE,
    ): GifsDto


    companion object {
        private const val LIMIT = 25
        private const val OFFSET = 0
        private const val RATING = "g"
        private const val LANGUAGE = "en"
    }

}