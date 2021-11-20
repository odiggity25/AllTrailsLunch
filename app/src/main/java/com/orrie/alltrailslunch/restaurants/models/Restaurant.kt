package com.orrie.alltrailslunch.restaurants.models

data class Restaurant(
    val id: String,
    val name: String,
    val stars: Int?,
    val dollarSigns: Int?,
    val numReviews: Int?,
    val supportingText: String?,
    val imageUrl: String?
)