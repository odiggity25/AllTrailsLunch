package com.orrie.alltrailslunch.restaurants.services

import com.google.android.gms.maps.model.LatLng
import com.orrie.alltrailslunch.R
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.restaurants.services.models.RestaurantServerModel
import com.orrie.alltrailslunch.restaurants.services.models.RestaurantsResultsServerModel
import com.orrie.alltrailslunch.shared.resourceString

/**
 * Used to map between client and server models related to restaurants
 *
 * Note to reviewer: By using mappers you can make the server model super lenient and handle
 * errors such as missing required fields here. That means if there is one messed up server model in
 * a list of models, the whole api result isn't corrupted.
 */
class RestaurantMapper {

    fun mapRestaurantResultsServerModelToRestaurants(restaurantsResultsServerModel: RestaurantsResultsServerModel)
    : List<Restaurant> {
        return restaurantsResultsServerModel.results.mapNotNull {
            mapRestaurantServerModelToRestaurant(it)
        }
    }

    private fun mapRestaurantServerModelToRestaurant(restaurantServerModel: RestaurantServerModel): Restaurant? {
        if (restaurantServerModel.id == null) return null
        if (restaurantServerModel.name == null) return null

        val lat = restaurantServerModel.geometry?.location?.lat?.toDouble()
        val lng = restaurantServerModel.geometry?.location?.lng?.toDouble()
        val location = if (lat != null && lng != null) LatLng(lat, lng) else null

        val photoUrl = restaurantServerModel.photos?.firstOrNull()?.photoReference?.let {
            "https://maps.googleapis.com/maps/api/place/photo?key=${R.string.google_maps_key.resourceString()}&photo_reference=$it&maxwidth=200"
        }

        return Restaurant(
            id = restaurantServerModel.id,
            name = restaurantServerModel.name,
            rating = restaurantServerModel.rating,
            priceLevel = restaurantServerModel.priceLevel,
            numReviews = restaurantServerModel.numRatings,
            supportingText = restaurantServerModel.supportingText,
            photoUrl = photoUrl,
            latLng = location
        )
    }
}