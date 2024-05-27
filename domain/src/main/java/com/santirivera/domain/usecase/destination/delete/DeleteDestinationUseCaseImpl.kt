package com.santirivera.domain.usecase.destination.delete

import com.santirivera.domain.repo.DestinationRepository

class DeleteDestinationUseCaseImpl(private val destinationRepository: DestinationRepository):
    DeleteDestinationUseCase(destinationRepository) {

    override suspend fun run(params: Int?): Boolean {
        return params?.let { destinationRepository.deleteById(params) }?:false
    }

}