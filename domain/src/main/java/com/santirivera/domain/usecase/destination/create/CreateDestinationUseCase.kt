package com.santirivera.domain.usecase.destination.create

import com.santirivera.domain.model.DestinationItem
import com.santirivera.domain.repo.DestinationRepository
import com.santirivera.domain.usecase.BaseUseCase

abstract class CreateDestinationUseCase(private val destinationRepository: DestinationRepository):
    BaseUseCase<DestinationItem, Boolean>()