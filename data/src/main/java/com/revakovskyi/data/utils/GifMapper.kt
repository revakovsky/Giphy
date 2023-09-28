package com.revakovskyi.data.utils

import com.revakovskyi.data.local.GifEntity
import com.revakovskyi.data.remote.dto.Data
import com.revakovskyi.domain.models.Gif

internal fun GifEntity.mapToGif(): Gif {
    return Gif(url = this.url)
}

internal fun Data.mapToGif(): Gif {
    return Gif(url = this.images.original.url)
}

internal fun Data.mapToGifEntity(): GifEntity {
    return GifEntity(url = this.images.original.url)
}
