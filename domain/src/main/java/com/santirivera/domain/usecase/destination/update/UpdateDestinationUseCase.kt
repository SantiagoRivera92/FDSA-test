package com.santirivera.domain.usecase.destination.update

import com.santirivera.domain.model.DestinationItem
import com.santirivera.domain.repo.DestinationRepository
import com.santirivera.domain.usecase.BaseUseCase

abstract class UpdateDestinationUseCase(private val destinationRepository: DestinationRepository):
    BaseUseCase<DestinationItem, Boolean>()