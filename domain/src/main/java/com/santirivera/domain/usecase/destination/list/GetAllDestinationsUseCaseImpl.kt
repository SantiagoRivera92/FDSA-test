package com.santirivera.domain.usecase.destination.list

import com.santirivera.domain.model.DestinationItem
import com.santirivera.domain.repo.DestinationRepository

class GetAllDestinationsUseCaseImpl(private val destinationRepository: DestinationRepository) :
    GetAllDestinationsUseCase(destinationRepository) {

    override suspend fun run(params: Void?): List<DestinationItem> {
        return destinationRepository.getAll()
    }

}