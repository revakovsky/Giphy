package com.revakovskyi.giphy.presentation.models

import com.revakovskyi.domain.models.Gif

data class GifUi(
    val url: String,
)

fun Gif.mapToGifUi(): GifUi = GifUi(url = this.url)
