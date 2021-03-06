package com.orrie.alltrailslunch.shared.views

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.orrie.alltrailslunch.shared.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseActivity<T: ViewBinding>(val bindingFactory: (LayoutInflater) -> T): AppCompatActivity() {

    protected lateinit var binding: T
    protected abstract val viewModel: BaseViewModel
    private val compositeDisposable = CompositeDisposable()

    // Note to reviewer: I like the next two abstract functions to be part of base activity, fragment and
    // custom views since there is almost always some UI work to initialize and view model observables to
    // subscribe to and this keeps the function names consistent. Additionally it helps those new to MVVM
    // to remember to keep the business logic in the view model.
    protected abstract fun initUi()
    protected abstract fun subscribeToViewModelObservables()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = bindingFactory(LayoutInflater.from(this))
        setContentView(binding.root)

        initUi()
        subscribeToViewModelObservables()
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