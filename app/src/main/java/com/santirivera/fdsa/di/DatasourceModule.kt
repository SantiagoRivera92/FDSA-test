package com.santirivera.fdsa.di

import android.content.Context
import android.content.res.AssetManager
import com.santirivera.data.api.DestinationsApi
import com.santirivera.data.api.DestinationsDataSource
import com.santirivera.data.api.DestinationsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Provides
    @Singleton
    fun providesAssetManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }

    @Provides
    @Singleton
    fun providesBeersDataSource(beersApi: DestinationsApi): DestinationsDataSource = DestinationsDataSourceImpl(beersApi)

}