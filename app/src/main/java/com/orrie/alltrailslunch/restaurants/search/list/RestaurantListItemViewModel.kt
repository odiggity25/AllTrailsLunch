package com.orrie.alltrailslunch.restaurants.search.list

import com.orrie.alltrailslunch.restaurants.RestaurantsRepository
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.shared.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.core.component.inject

/**
 * This is a restaurant search result card in the recyclerview and contains info
 * including name, image, price and rating
 */
class RestaurantListItemViewModel : BaseViewModel() {

    // Observables
    val heartChanged: Observable<Boolean> = PublishSubject.create()

    // Properties
    private var restaurant: Restaurant? = null
    private var isHearted: Boolean = false
        set(value) {
            field = value
            heartChanged.onNext(value)
        }
    private val restaurantsRepository: RestaurantsRepository by inject()

    // Functions
    fun bind(restaurant: Restaurant) {
        this.restaurant = restaurant
        isHearted = restaurantsRepository.isRestaurantHearted(restaurant.id)
    }

    fun heartTapped() {
        isHearted = !isHearted
        restaurant?.id?.let { restaurantsRepository.setRestaurantHearted(it, isHearted) }
    }
}