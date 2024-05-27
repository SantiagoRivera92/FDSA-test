package com.santirivera.data.room

import androidx.room.*
import com.santirivera.data.api.model.ResponseDestination

@Dao
interface ResponseDestinationDao {

    @Query("SELECT * FROM response_destination")
    suspend fun getAllDestinations(): List<ResponseDestination>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(destinations: List<ResponseDestination>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(destination: ResponseDestination)

    @Query("SELECT * FROM response_destination WHERE id IN (:ids)")
    suspend fun getDestinationsByIds(ids: List<Int>): List<ResponseDestination>

    @Query("SELECT * FROM response_destination WHERE id = :id")
    suspend fun getDestinationById(id: Int): ResponseDestination?

    @Transaction
    suspend fun updateIfMoreRecentAndReturnOutdated(destinations: List<ResponseDestination>) : List<ResponseDestination> {
        val existingDestinations = getDestinationsByIds(destinations.map { it.id })
        val destinationsToUpdate = ArrayList<ResponseDestination>()
        val outdatedDestinationsInSource = ArrayList<ResponseDestination>()

        for (newDestination in destinations) {
            val existingDestination = existingDestinations.find { it.id == newDestination.id }
            if (existingDestination == null || newDestination.lastModify > existingDestination.lastModify) {
                destinationsToUpdate.add(newDestination)
            } else{
                // Our existing data is more recent, which means we created new entries while offline: Add it to the list to update it on backend
                outdatedDestinationsInSource.add(existingDestination)
            }
        }

        if (destinationsToUpdate.isNotEmpty()) {
            insertAll(destinationsToUpdate)
        }

        return existingDestinations
    }

    @Query("DELETE FROM response_destination WHERE id = :id")
    suspend fun deleteDestinationById(id: Int)

    @Transaction
    suspend fun updateDestination(destination: ResponseDestination){
        val existingDestination = getDestinationById(destination.id)
        if (existingDestination == null || destination.lastModify > existingDestination.lastModify){
            insert(destination)
        }
    }

    @Query("SELECT MAX(id) FROM response_destination")
    suspend fun getMaxId(): Int?

    @Transaction
    suspend fun getNewId(): Int {
        val maxId = getMaxId() ?: 0
        return maxId + 1
    }
}
