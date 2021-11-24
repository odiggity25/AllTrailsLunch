package com.orrie.alltrailslunch.restaurants.search

import android.location.Location
import com.orrie.alltrailslunch.restaurants.RestaurantsRepository
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.shared.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.core.component.inject
import timber.log.Timber
import java.util.concurrent.TimeUnit

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
    val currentLocationRequested: Observable<Unit> = BehaviorSubject.create()
    val loadingSpinnerVisibilityChanged: Observable<Boolean> = PublishSubject.create()

    // Properties
    private val restaurantsRepository: RestaurantsRepository by inject()
    private var viewType: ViewType = ViewType.Map
        set(value) {
            searchViewTypeChanged.onNext(value)
            field = value
        }
    private var location: Location? = null
    private var searchQuery: String? = null
    private val searchQueryChanged = PublishSubject.create<String>()

    // Functions
    init {
        viewType = ViewType.Map
        currentLocationRequested.onNext(Unit)
        subscribeToSearchQueryChange()
    }

    private fun subscribeToSearchQueryChange() {
        searchQueryChanged
            .skip(1) // First that comes through is just empty string
            .distinctUntilChanged()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe{ searchNearby() }.autoDispose()
    }

    fun listMapToggleTapped() {
        viewType = when (viewType) {
            ViewType.Map -> ViewType.List
            ViewType.List -> ViewType.Map
        }
    }

    fun locationUpdated(location: Location?) {
        this.location = location
        searchNearby()
    }

    private fun searchNearby() {
        val location = this.location ?: return
        restaurantsRepository.searchNearby(location, searchQuery)
            .doOnSubscribe { loadingSpinnerVisibilityChanged.onNext(true) }
            .doOnTerminate { loadingSpinnerVisibilityChanged.onNext(false) }
            .subscribe({
                searchResultsChanged.onNext(it)
            }, {
                Timber.e(it)
            }).autoDispose()
    }

    fun searchTextChanged(query: String) {
        searchQuery = query
        searchQueryChanged.onNext(query)
    }

    enum class ViewType { List, Map }
}