package com.revakovskyi.data.mapper

import com.revakovskyi.data.local.GifEntity
import com.revakovskyi.data.remote.dto.Data
import com.revakovskyi.domain.models.Gif

fun GifEntity.mapToGif(): Gif {
    return Gif(url = this.url)
}

fun Data.mapToGifEntity(): GifEntity {
    return GifEntity(url = this.images.original.url)
}
