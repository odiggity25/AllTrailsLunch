package com.orrie.alltrailslunch.restaurants.search.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orrie.alltrailslunch.R
import com.orrie.alltrailslunch.databinding.RestaurantListItemViewBinding
import com.orrie.alltrailslunch.databinding.RestaurantsListFragmentBinding
import com.orrie.alltrailslunch.shared.viewModels.BaseViewModel
import com.orrie.alltrailslunch.shared.views.BaseFragment
import org.koin.android.ext.android.inject

class RestaurantsListFragment : BaseFragment<RestaurantsListFragmentBinding>(
    RestaurantsListFragmentBinding::inflate
) {
    // Properties
    override val viewModel: RestaurantsListViewModel by inject()

    // Functions
    override fun initUi() {
        TODO("Not yet implemented")
    }

    override fun subscribeToViewModelObservables() {
        TODO("Not yet implemented")
    }

}