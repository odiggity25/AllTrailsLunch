package com.orrie.alltrailslunch.shared.views

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import com.orrie.alltrailslunch.ATApplication
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

/**
 * This makes it easy to throttle view clicks so that accidental quick successive taps
 * are only registered as one tap.
 */
fun View.throttleTaps(action: () -> Unit) {
    clicks().throttleFirst(300, TimeUnit.MILLISECONDS).subscribe { action() }
}

/**
 * This is just a convenience way for views to subscribe to view model observables
 * since they always need to observe on the main thread and we don't need to worry
 * about errors since the view model should never be propagating errors. If there
 * is an error in a view model observable it should be handling it and exposing a
 * different observable such as errorMessageUpdated so the view knows to show the error.
 */
fun <T: Any> Observable<T>.subscribeToViewModelObservable(action: (value: T) -> Unit) {
    observeOn(AndroidSchedulers.mainThread()).subscribe { action(it) }
}

fun Int.pxToDp(): Int = (this / ATApplication.context.resources.displayMetrics.density).toInt()

fun Int.dpToPx(): Int = (this * ATApplication.context.resources.displayMetrics.density).roundToInt()

fun View.setVisibility(visible: Boolean, valueIfNotVisible: Int = View.INVISIBLE) {
    visibility = if (visible) View.VISIBLE else valueIfNotVisible
}