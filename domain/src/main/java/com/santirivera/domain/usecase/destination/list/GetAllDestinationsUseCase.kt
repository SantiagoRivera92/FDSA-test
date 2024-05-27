package com.santirivera.domain.usecase.destination.list

import com.santirivera.domain.model.DestinationItem
import com.santirivera.domain.repo.DestinationRepository
import com.santirivera.domain.usecase.BaseUseCase

abstract class GetAllDestinationsUseCase(private val destinationRepository: DestinationRepository):
    BaseUseCase<Void, List<DestinationItem>>()