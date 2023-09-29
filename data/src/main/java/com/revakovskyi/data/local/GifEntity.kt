package com.revakovskyi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GifEntity")
internal data class GifEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val url: String,
)
