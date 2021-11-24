package com.orrie.alltrailslunch.restaurants

import com.orrie.alltrailslunch.shared.LocalPrefs
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val HEARTED_IDS = "heartedIds"

/**
 * This manages local persistence of restaurant related values
 * Note to reviewer: I'm just using shared preferences to manage the favourites as it's just id's and
 * using a db is overkill.
 */
class RestaurantsStore: KoinComponent {

    private val localPrefs: LocalPrefs by inject()

    fun fetchHeartedRestaurantIds(): Set<String> {
        return localPrefs.getSet(HEARTED_IDS) ?: setOf()
    }

    fun updateHeartedRestaurantIds(ids: Set<String>) {
        localPrefs.putSet(HEARTED_IDS, ids)
    }
}