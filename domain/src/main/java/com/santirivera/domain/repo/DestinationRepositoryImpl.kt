package com.santirivera.domain.repo

import com.santirivera.data.api.DestinationsDataSource
import com.santirivera.data.room.ResponseDestinationDao
import com.santirivera.domain.model.DestinationItem
import com.santirivera.domain.model.toDestinationItemList
import com.santirivera.domain.model.toResponseDestination

class DestinationRepositoryImpl(
    private val dataSource: DestinationsDataSource,
    private val dao: ResponseDestinationDao
) : DestinationRepository {

    override suspend fun getAll(): List<DestinationItem> {

        val fromApi = dataSource.getAll()
        val outdatedInApi = dao.updateIfMoreRecentAndReturnOutdated(fromApi)
        for (destination in outdatedInApi){
            dataSource.update(destination)
        }
        val destinations = dao.getAllDestinations().toDestinationItemList()
        println("There's " + destinations.size + "destinations")
        return destinations
    }

    override suspend fun deleteById(id: Int): Boolean {
        dao.deleteDestinationById(id)
        return dataSource.deleteById(id)
    }

    override suspend fun create(destination: DestinationItem):Boolean {
        /**
         * In the real world, we'd just send the response to the API,
         * which would assign a valid ID to it, and then we'd update
         * our local database when we next query for changes.
         */
        val validId = dao.getNewId()
        val newDestination = DestinationItem(
            id=validId,
            name=destination.name,
            description = destination.description,
            countryCode = destination.countryCode,
            destinationType = destination.destinationType)
        dao.insert(newDestination.toResponseDestination())
        return dataSource.create(newDestination.toResponseDestination())
    }

    override suspend fun update(destination: DestinationItem):Boolean {
        dao.updateDestination(destination.toResponseDestination())
        return dataSource.update(destination.toResponseDestination())
    }
}