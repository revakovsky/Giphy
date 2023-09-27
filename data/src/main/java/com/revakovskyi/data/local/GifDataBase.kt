package com.revakovskyi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GifEntity::class], version = 1)
internal abstract class GifDataBase : RoomDatabase() {

    abstract val dao: GifDao

}