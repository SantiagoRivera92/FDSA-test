package com.santirivera.data.api

import com.santirivera.data.api.model.ResponseDestination
import retrofit2.http.Body


interface DestinationsDataSource {

    @Throws(Exception::class)
    suspend fun getAll(): List<ResponseDestination>

    @Throws(Exception::class)
    suspend fun deleteById(id:Int): Boolean

    @Throws(Exception::class)
    suspend fun update(destination: ResponseDestination): Boolean

    @Throws(Exception::class)
    suspend fun create(destination: ResponseDestination): Boolean
}