package com.santirivera.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.santirivera.data.api.model.ResponseDestination

@Database(entities = [ResponseDestination::class], version = 1)
abstract class DestinationDatabase : RoomDatabase() {
    abstract fun responseDestinationDao(): ResponseDestinationDao
}
