package com.orrie.alltrailslunch.restaurants

import com.orrie.alltrailslunch.restaurants.models.Restaurant
import io.reactivex.rxjava3.core.Single

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

    fun searchNearby(): Single<List<Restaurant>> {
        // TODO
        return Single.just(listOf(
            Restaurant(
                id = "1",
                name = "Res 1",
                stars = 3,
                dollarSigns = 2,
                numReviews = 211,
                supportingText = "eat here!",
                imageUrl = "https://theoceanclubdestin.com/wp-content/uploads/2019/10/Destin-restaurant-wine-with-dinner.jpg"
            ),
            Restaurant(
                id = "2",
                name = "Res 2",
                stars = 1,
                dollarSigns = 1,
                numReviews = 221,
                supportingText = "don't eat here!",
                imageUrl = "https://www.google.com/imgres?imgurl=https%3A%2F%2Foceananniesresorts.com%2Fwp-content%2Fuploads%2F2019%2F03%2Fmyrtle-beach-restaurants.jpg&imgrefurl=https%3A%2F%2Foceananniesresorts.com%2Fmyrtle-beach-restaurants-ocean-annies%2F&tbnid=HV_F0cFQl6aPeM&vet=12ahUKEwjE0dSJ4Kr0AhWLhFMKHSfBAAUQMygBegUIARDKAQ..i&docid=vBzbAAco2eC41M&w=5802&h=4000&q=restaurant&ved=2ahUKEwjE0dSJ4Kr0AhWLhFMKHSfBAAUQMygBegUIARDKAQ"
            )
        ))
    }
}