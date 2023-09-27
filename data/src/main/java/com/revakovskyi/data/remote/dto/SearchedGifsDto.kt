package com.revakovskyi.data.remote.dto

import com.google.gson.annotations.SerializedName

internal data class SearchedGifsDto(
    @SerializedName("data") val data: List<Data>,
)
