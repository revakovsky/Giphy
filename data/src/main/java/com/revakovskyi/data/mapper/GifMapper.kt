package com.revakovskyi.data.mapper

import com.revakovskyi.data.local.GifEntity
import com.revakovskyi.data.remote.dto.Original
import com.revakovskyi.domain.models.Gif

fun GifEntity.mapToGif(): Gif {
    return Gif(url = this.url)
}

fun Original.mapToGifsEntity(): GifEntity {
    return GifEntity(url = this.url)
}
