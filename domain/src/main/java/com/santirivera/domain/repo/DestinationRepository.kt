package com.santirivera.domain.repo

import com.santirivera.domain.model.DestinationItem

interface DestinationRepository {

    suspend fun getAll(): List<DestinationItem>

    suspend fun deleteById(id:Int): Boolean

    suspend fun create(destination:DestinationItem) : Boolean

    suspend fun update(destination:DestinationItem) : Boolean

}