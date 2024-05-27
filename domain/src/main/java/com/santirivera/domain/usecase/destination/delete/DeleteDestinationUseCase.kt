package com.santirivera.domain.usecase.destination.delete

import com.santirivera.domain.repo.DestinationRepository
import com.santirivera.domain.usecase.BaseUseCase

abstract class DeleteDestinationUseCase(private val destinationRepository: DestinationRepository):
    BaseUseCase<Int, Boolean>()