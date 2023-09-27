package com.revakovskyi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchGifsDto(
    @SerializedName("data") val data: List<Data>,
)
