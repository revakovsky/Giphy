package com.revakovskyi.data.remote.dto

import com.google.gson.annotations.SerializedName

internal data class Data(
    @SerializedName("images") val images: Images,
)


internal data class Images(
    @SerializedName("original") val original: Original
)


internal data class Original(
    @SerializedName("url") val url: String,
)
