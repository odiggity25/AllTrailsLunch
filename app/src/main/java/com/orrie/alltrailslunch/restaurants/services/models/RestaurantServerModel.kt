package com.orrie.alltrailslunch.restaurants.services.models

import com.google.gson.annotations.SerializedName

/**
 * Note to reviewer: This may seem strange to have a separate model for the services, however I'm a firm
 * believer in decoupling the server model from the client model. It removes complexity from the client code,
 * enables better error handling and makes to adapt to api changes. The server models are converted to client
 * models using Mapper classes which can be unit tested.
 */
data class RestaurantServerModel(
    @SerializedName("place_id") val id: String? = null,
    val name: String? = null,
    val rating: Double? = null,
    @SerializedName("price_level") val priceLevel: Int? = null,
    @SerializedName("user_ratings_total") val numRatings: Int? = null,
    // Couldn't find anything in the json to user here?
    // Would have clarified this in real life
    val supportingText: String? = "Eat here!",
    val geometry: GeometryServerModel? = null,
    val photos: List<PlacePhoto>? = null
)

data class GeometryServerModel(
    val location: LocationServerModel? = null
)

data class LocationServerModel(
    val lat: Float? = null,
    val lng: Float? = null
)

data class PlacePhoto(
    @SerializedName("photo_reference") val photoReference: String? = null
)
