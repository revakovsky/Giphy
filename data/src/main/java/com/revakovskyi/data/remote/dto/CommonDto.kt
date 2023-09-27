package com.revakovskyi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("images") val images: Images,
)


data class Images(
    @SerializedName("original") val original: Original
)


data class Original(
    @SerializedName("url") val url: String,
)
