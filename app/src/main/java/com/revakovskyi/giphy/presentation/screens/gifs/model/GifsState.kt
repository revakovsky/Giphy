package com.revakovskyi.giphy.presentation.screens.gifs.model

import com.revakovskyi.giphy.core.QueryManager

data class GifsState(
    val gifsUrls: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val enteredQuery: String = "",
    val queryVerificationStatus: QueryManager.Status = QueryManager.Status.Neutral,
    val shouldCloseTheApp: Boolean = false,
    val chosenGifUrl: String = "",
)
