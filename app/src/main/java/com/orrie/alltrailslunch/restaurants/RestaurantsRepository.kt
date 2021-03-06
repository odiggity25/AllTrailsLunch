package com.orrie.alltrailslunch.restaurants

import android.location.Location
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.restaurants.services.RestaurantsService
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Note to reviewer: Repositories are the bridge between view models and the storage layers
 */
class RestaurantsRepository: KoinComponent {

    private val restaurantsService: RestaurantsService by inject()
    private val restaurantsStore: RestaurantsStore by inject()
    private val heartedCache = restaurantsStore.fetchHeartedRestaurantIds().toMutableSet()

    fun isRestaurantHearted(id: String): Boolean {
        return heartedCache.contains(id)
    }

    fun setRestaurantHeartedState(id: String, isHearted: Boolean) {
        if (isHearted) heartedCache.add(id) else heartedCache.remove(id)
        restaurantsStore.updateHeartedRestaurantIds(heartedCache)
    }

    fun searchNearby(location: Location, keyword: String? = null): Single<List<Restaurant>> {
        return restaurantsService.getNearbyRestaurants(location, keyword)
    }
}