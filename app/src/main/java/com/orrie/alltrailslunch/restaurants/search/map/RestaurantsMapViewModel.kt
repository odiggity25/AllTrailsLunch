package com.orrie.alltrailslunch.restaurants.search.map

import com.google.android.gms.maps.model.MarkerOptions
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.shared.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * Shows restaurants as tappable pins on a map
 */
class RestaurantsMapViewModel : BaseViewModel() {

    // Observables
    val markersUpdated: Observable<List<MarkerOptionsWithRestaurant>> = BehaviorSubject.create()

    // Functions
    fun restaurantsUpdated(restaurants: List<Restaurant>) {
        markersUpdated.onNext(
            restaurants.filter { it.latLng != null }
                .map {
                    MarkerOptionsWithRestaurant(MarkerOptions().position(it.latLng!!), it)
                }
        )
    }

    data class MarkerOptionsWithRestaurant(val markerOptions: MarkerOptions, val restaurant: Restaurant)
}