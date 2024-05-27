package com.santirivera.domain.usecase.destination.create

import com.santirivera.domain.model.DestinationItem
import com.santirivera.domain.repo.DestinationRepository

class CreateDestinationUseCaseImpl(private val destinationRepository: DestinationRepository):
    CreateDestinationUseCase(destinationRepository) {

    override suspend fun run(params: DestinationItem?): Boolean {
        return params?.let { destinationRepository.create(params)}?:false
    }

}