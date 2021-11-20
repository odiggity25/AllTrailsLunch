package com.orrie.alltrailslunch.shared.viewModels

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import org.koin.core.component.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent {

    /**
     * This allows us to expose subjects as observables to our views and still
     * be able to call onNext within the ViewModel.
     */
    fun <T: Any> Observable<T>.onNext(value: T) {
        (this as Subject).onNext(value)
    }
}