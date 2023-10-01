package com.revakovskyi.data.utils

import com.revakovskyi.data.local.GifEntity
import com.revakovskyi.data.remote.dto.Data

internal fun Data.mapToGifEntity(): GifEntity {
    return GifEntity(url = this.images.original.url)
}
