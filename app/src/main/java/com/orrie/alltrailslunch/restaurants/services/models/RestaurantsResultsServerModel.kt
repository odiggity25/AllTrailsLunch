package com.orrie.alltrailslunch.restaurants.services.models

data class RestaurantsResultsServerModel(
    // Note to reviewer: I'm ignoring pagination, outside of scope
    val results: List<RestaurantServerModel>
)