package com.revakovskyi.giphy.presentation.screens.gifs.mvi

import com.revakovskyi.giphy.presentation.models.GifUi

internal data class GifsState(
    val gifs: List<GifUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)
