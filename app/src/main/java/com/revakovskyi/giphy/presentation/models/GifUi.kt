package com.revakovskyi.giphy.presentation.models

import com.revakovskyi.domain.models.Gif

internal data class GifUi(
    val url: String,
)

internal fun Gif.mapToGifUi(): GifUi = GifUi(url = this.url)
