package com.santirivera.data.api

import com.santirivera.data.api.model.ResponseDestination

class DestinationsDataSourceImpl(

    private val destinationsApi: DestinationsApi,
) : DestinationsDataSource {

    override suspend fun getAll(): ArrayList<ResponseDestination> {
        return destinationsApi.getAll()
    }

    override suspend fun deleteById(id: Int): Boolean {
        return destinationsApi.deleteById(id)
    }

    override suspend fun update(destination: ResponseDestination): Boolean {
        return destinationsApi.update(destination)
    }

    override suspend fun create(destination: ResponseDestination): Boolean {
        return destinationsApi.create(destination)
    }
}