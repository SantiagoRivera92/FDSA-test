package com.santirivera.fdsa.di

import com.santirivera.data.api.DestinationsDataSource
import com.santirivera.data.room.ResponseDestinationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.santirivera.domain.repo.DestinationRepository
import com.santirivera.domain.repo.DestinationRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesDestinationRepository(
        dataSource: DestinationsDataSource,
        dao: ResponseDestinationDao
    ): DestinationRepository = DestinationRepositoryImpl(dataSource, dao)

}