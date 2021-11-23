package com.orrie.alltrailslunch.restaurants.search

import com.orrie.alltrailslunch.restaurants.RestaurantsRepository
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.shared.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.koin.core.component.inject
import timber.log.Timber

/**
 * This is the view model for the main activity that houses the search bar along with the map fragment and
 * list fragment for the search results
 */
class RestaurantsSearchViewModel : BaseViewModel() {

    // Observables
    // Note to reviewer: I have used LiveData extensively and really dislike it.
    // Happy to discuss more in a code review
    val searchViewTypeChanged: Observable<ViewType> = BehaviorSubject.create()
    val searchResultsChanged: Observable<List<Restaurant>> = BehaviorSubject.create()

    // Properties
    private val restaurantsRepository: RestaurantsRepository by inject()
    private var viewType: ViewType = ViewType.Map
        set(value) {
            searchViewTypeChanged.onNext(value)
            field = value
        }

    // Functions
    init {
        viewType = ViewType.Map
        restaurantsRepository.searchNearby()
            .subscribe({
                searchResultsChanged.onNext(it)
            }, {
                Timber.e(it)
            })
    }

    fun listMapToggleTapped() {
        viewType = when (viewType) {
            ViewType.Map -> ViewType.List
            ViewType.List -> ViewType.Map
        }
    }

    enum class ViewType { List, Map }
}