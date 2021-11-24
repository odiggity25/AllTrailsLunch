package com.orrie.alltrailslunch.shared.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.orrie.alltrailslunch.shared.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFragment<T: ViewBinding>(val bindingFactory: (LayoutInflater) -> T) : Fragment() {

    protected lateinit var binding: T
    protected abstract val viewModel: BaseViewModel
    private val compositeDisposable = CompositeDisposable()

    // Note to reviewer: I like the next two abstract functions to be part of base activity, fragment and
    // custom views since there is almost always some UI work to initialize and view model observables to
    // subscribe to and this keeps the function names consistent. Additionally it helps those new to MVVM
    // to remember to keep the business logic in the view model.
    protected abstract fun initUi()
    protected abstract fun subscribeToViewModelObservables()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingFactory(inflater)

        initUi()
        subscribeToViewModelObservables()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }

    protected fun Disposable.autoDispose() {
        compositeDisposable.add(this)
    }

    protected fun List<Disposable>.autoDispose() {
        this.forEach { compositeDisposable.add(it) }
    }
}