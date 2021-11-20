package com.orrie.alltrailslunch.restaurants.search

import com.orrie.alltrailslunch.shared.viewModels.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * This is the view model for the main activity that houses the search bar along with the map fragment and
 * list fragment for the search results
 */
class MainViewModel : BaseViewModel() {

    // Observables
    // Note to reviewer: I have used LiveData extensively and really dislike it.
    // Happy to discuss more in a code review
    val viewStateChanged: Observable<ViewState> = PublishSubject.create()

    // Properties
    private var viewState: ViewState = ViewState.Map
        set(value) {
            viewStateChanged.onNext(value)
            field = value
        }

    fun listMapToggleTapped() {
        viewState = when (viewState) {
            ViewState.Map -> ViewState.List
            ViewState.List -> ViewState.Map
        }
    }

    enum class ViewState { List, Map }
}