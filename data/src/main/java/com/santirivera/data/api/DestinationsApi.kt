package com.santirivera.data.api

import com.santirivera.data.api.model.ResponseDestination
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface DestinationsApi {

    @GET("GetAll")
    suspend fun getAll(): ArrayList<ResponseDestination>

    @DELETE("DeleteById")
    suspend fun deleteById(id:Int): Boolean

    @POST("Create")
    suspend fun create(@Body destination:ResponseDestination): Boolean

    @POST("Update")
    suspend fun update(@Body destination:ResponseDestination): Boolean

}