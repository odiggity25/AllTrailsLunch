package com.orrie.alltrailslunch.shared

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject

class BaseRepository {

    /**
     * This allows us to expose subjects as observables and still
     * be able to call onNext within the repository.
     */
    fun <T: Any> Observable<T>.onNext(value: T) {
        (this as Subject).onNext(value)
    }
}