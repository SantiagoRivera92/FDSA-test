package com.santirivera.fdsa.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.santirivera.domain.repo.DestinationRepository
import com.santirivera.domain.usecase.destination.create.CreateDestinationUseCase
import com.santirivera.domain.usecase.destination.create.CreateDestinationUseCaseImpl
import com.santirivera.domain.usecase.destination.delete.DeleteDestinationUseCase
import com.santirivera.domain.usecase.destination.delete.DeleteDestinationUseCaseImpl
import com.santirivera.domain.usecase.destination.list.GetAllDestinationsUseCase
import com.santirivera.domain.usecase.destination.list.GetAllDestinationsUseCaseImpl
import com.santirivera.domain.usecase.destination.update.UpdateDestinationUseCase
import com.santirivera.domain.usecase.destination.update.UpdateDestinationUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun providesDestinationsListUseCase(destinationRepository: DestinationRepository):
            GetAllDestinationsUseCase = GetAllDestinationsUseCaseImpl(destinationRepository)

    @Provides
    fun providesDeleteDestinationUseCase(destinationRepository: DestinationRepository):
            DeleteDestinationUseCase = DeleteDestinationUseCaseImpl(destinationRepository)

    @Provides
    fun providesUpdateDestinationUseCase(destinationRepository: DestinationRepository):
            UpdateDestinationUseCase = UpdateDestinationUseCaseImpl(destinationRepository)

    @Provides
    fun providesCreateDestinationUseCase(destinationRepository: DestinationRepository):
            CreateDestinationUseCase = CreateDestinationUseCaseImpl(destinationRepository)
}