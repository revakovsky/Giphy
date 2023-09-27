package com.revakovskyi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface GifDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGif(gifEntities: List<GifEntity>)

    @Query("DELETE FROM GifEntity")
    suspend fun clearLocalDb()

    @Query("SELECT * FROM GifEntity")
    suspend fun provideGifs(): List<GifEntity>

}