package com.orrie.alltrailslunch.restaurants.models

import com.google.android.gms.maps.model.LatLng

data class Restaurant(
    val id: String,
    val name: String,
    val rating: Double?,
    val priceLevel: Int?,
    val numReviews: Int?,
    val supportingText: String?,
    val latLng: LatLng?,
    val photoUrl: String?
)