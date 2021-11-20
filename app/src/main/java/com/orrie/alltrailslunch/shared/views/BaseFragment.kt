package com.orrie.alltrailslunch.shared.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.orrie.alltrailslunch.shared.viewModels.BaseViewModel

abstract class BaseFragment<T: ViewBinding>(val bindingFactory: (LayoutInflater) -> T) : Fragment() {

    protected lateinit var binding: T

    protected abstract val viewModel: BaseViewModel
    protected abstract fun initUi()
    protected abstract fun subscribeToViewModelObservables()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingFactory(inflater).also { binding = it }.root
    }
}