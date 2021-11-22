package com.orrie.alltrailslunch.restaurants.search.list

import androidx.recyclerview.widget.LinearLayoutManager
import com.orrie.alltrailslunch.databinding.RestaurantsListFragmentBinding
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.shared.views.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantsListFragment : BaseFragment<RestaurantsListFragmentBinding>(
    RestaurantsListFragmentBinding::inflate
) {
    // Properties
    override val viewModel: RestaurantsListViewModel by viewModel()
    private val adapter: RestaurantsListAdapter by inject()

    // Functions
    override fun initUi() {
        binding.restaurantsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RestaurantsListFragment.adapter
        }
    }

    override fun subscribeToViewModelObservables() {
        // No-op in this case. Normally this is not the case for fragments in real apps
    }

    fun updateResults(restaurants: List<Restaurant>) {
        adapter.restaurants = restaurants
    }
}