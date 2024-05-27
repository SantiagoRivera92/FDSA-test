package com.santirivera.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DestinationItem(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val countryCode: String = "",
    val destinationType: String = "",
    val lastModify: Long = 0
) : Parcelable
