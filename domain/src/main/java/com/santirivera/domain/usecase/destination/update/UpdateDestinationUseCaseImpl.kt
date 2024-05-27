package com.santirivera.domain.usecase.destination.update

import com.santirivera.domain.model.DestinationItem
import com.santirivera.domain.repo.DestinationRepository

class UpdateDestinationUseCaseImpl(private val destinationRepository: DestinationRepository):
    UpdateDestinationUseCase(destinationRepository) {

    override suspend fun run(params: DestinationItem?): Boolean {
        return params?.let { destinationRepository.update(params)}?:false
    }

}