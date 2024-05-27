package com.santirivera.fdsa.di

import android.content.Context
import androidx.room.Room
import com.santirivera.data.room.DestinationDatabase
import com.santirivera.data.room.ResponseDestinationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DestinationDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DestinationDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideResponseDestinationDao(database: DestinationDatabase): ResponseDestinationDao {
        return database.responseDestinationDao()
    }
}
