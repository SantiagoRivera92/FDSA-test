package com.santirivera.data.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "response_destination")
data class ResponseDestination(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val countryCode: String,
    val destinationType: String,
    val lastModify: Long
)