package com.orrie.alltrailslunch.restaurants.services

import android.location.Location
import com.orrie.alltrailslunch.R
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.restaurants.services.models.RestaurantsResultsServerModel
import com.orrie.alltrailslunch.shared.resourceString
import com.orrie.alltrailslunch.shared.services.BaseService
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsApi {
    @GET("/maps/api/place/nearbysearch/json?type=restaurant&radius=1500")
    fun getNearbyRestaurants(
        @Query("location") location: String,
        @Query("keyword") keyword: String? = null,
        @Query("key") key: String? = R.string.google_maps_key.resourceString()
    ): Single<RestaurantsResultsServerModel>
}

class RestaurantsService : BaseService<RestaurantsApi>(RestaurantsApi::class.java), KoinComponent {
    private val restaurantMapper: RestaurantMapper by inject()

    fun getNearbyRestaurants(
        location: Location,
        keyword: String? = null
    ): Single<List<Restaurant>> {
        return service.getNearbyRestaurants(
            location = "${location.latitude},${location.longitude}",
            keyword = keyword
        ).map(restaurantMapper::mapRestaurantResultsServerModelToRestaurants)
    }
}