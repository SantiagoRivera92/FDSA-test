package com.santirivera.domain.model

import com.santirivera.data.api.model.ResponseDestination

fun ResponseDestination.toDestinationItem(): DestinationItem {
    return DestinationItem(id, name, description, countryCode, destinationType, lastModify)
}

fun List<ResponseDestination>.toDestinationItemList(): List<DestinationItem> {
    val list = ArrayList<DestinationItem>()
    for (destination in this) {
        list.add(destination.toDestinationItem())
    }
    return list
}

fun DestinationItem.toResponseDestination(): ResponseDestination{
    return ResponseDestination(id, name, description, countryCode, destinationType, lastModify)
}