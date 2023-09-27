package com.revakovskyi.data.remote.dto

import com.google.gson.annotations.SerializedName

internal data class TrendingGifsDto(
    @SerializedName("data") val data: List<Data>,
)
