package com.orrie.alltrailslunch.restaurants

class RestaurantsRepository {

    val heartedCache = mutableMapOf<String, Boolean>()

    fun isRestaurantHearted(id: String): Boolean {
        // TODO: Fetch from db
        return heartedCache[id] ?: false
    }

    fun setRestaurantHearted(id: String, hearted: Boolean) {
        // TODO: Update db
        heartedCache[id] = hearted
    }
}