package com.revakovskyi.giphy.presentation.screens.gifs.mvi

import com.revakovskyi.giphy.core.QueryManager
import com.revakovskyi.giphy.presentation.models.GifUi

data class GifsState(
    val gifs: List<GifUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val enteredQuery: String = "",
    val queryVerificationStatus: QueryManager.Status = QueryManager.Status.Neutral,
    val shouldCloseTheApp: Boolean = false,
)
