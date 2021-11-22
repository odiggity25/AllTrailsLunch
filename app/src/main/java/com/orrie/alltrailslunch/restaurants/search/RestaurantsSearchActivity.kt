package com.orrie.alltrailslunch.restaurants.search

import android.view.View
import com.orrie.alltrailslunch.R
import com.orrie.alltrailslunch.databinding.RestaurantsSearchActivityBinding
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantsListFragment
import com.orrie.alltrailslunch.restaurants.search.map.RestaurantsMapFragment
import com.orrie.alltrailslunch.shared.resourceString
import com.orrie.alltrailslunch.shared.views.BaseActivity
import com.orrie.alltrailslunch.shared.views.subscribeToViewModelObservable
import com.orrie.alltrailslunch.shared.views.throttleTaps
import org.koin.android.ext.android.inject

/**
 * This is the main activity that houses the search bar along with the map fragment and
 * list fragment for the search results
 */
class RestaurantsSearchActivity : BaseActivity<RestaurantsSearchActivityBinding>(
    RestaurantsSearchActivityBinding::inflate
) {

    private lateinit var mapFragment: RestaurantsMapFragment
    private lateinit var listFragment: RestaurantsListFragment
    override val viewModel: RestaurantsSearchViewModel by inject()

    override fun initUi() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as RestaurantsMapFragment
        listFragment = supportFragmentManager.findFragmentById(R.id.list_fragment) as RestaurantsListFragment

        binding.listMapToggle.throttleTaps { viewModel.listMapToggleTapped() }
    }

    override fun subscribeToViewModelObservables() {
        viewModel.apply {
            searchViewTypeChanged.subscribeToViewModelObservable { updateVisibleFragment(it) }
            searchResultsChanged.subscribeToViewModelObservable {
                mapFragment.updateResults(it)
                listFragment.updateResults(it)
            }
        }
    }

    private fun updateVisibleFragment(viewType: RestaurantsSearchViewModel.ViewType) {
        when (viewType) {
            RestaurantsSearchViewModel.ViewType.List -> {
                binding.listMapToggle.text = R.string.map.resourceString()
                binding.listFragmentContainer.visibility = View.VISIBLE
                binding.mapFragmentContainer.visibility = View.GONE
            }
            RestaurantsSearchViewModel.ViewType.Map -> {
                binding.listMapToggle.text = R.string.list.resourceString()
                binding.listFragmentContainer.visibility = View.GONE
                binding.mapFragmentContainer.visibility = View.VISIBLE
            }
        }
    }
}