package com.orrie.alltrailslunch.restaurants.search

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxbinding4.widget.textChanges
import com.orrie.alltrailslunch.R
import com.orrie.alltrailslunch.databinding.RestaurantsSearchActivityBinding
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantsListFragment
import com.orrie.alltrailslunch.restaurants.search.map.RestaurantsMapFragment
import com.orrie.alltrailslunch.shared.resourceDrawable
import com.orrie.alltrailslunch.shared.resourceString
import com.orrie.alltrailslunch.shared.toast
import com.orrie.alltrailslunch.shared.views.BaseActivity
import com.orrie.alltrailslunch.shared.views.setVisibility
import com.orrie.alltrailslunch.shared.views.subscribeToViewModelObservable
import com.orrie.alltrailslunch.shared.views.throttleTaps
import com.tbruyelle.rxpermissions3.RxPermissions
import org.koin.android.ext.android.inject

/**
 * This is the main activity that houses the search bar along with the map fragment and
 * list fragment for the search results
 */
class RestaurantsSearchActivity : BaseActivity<RestaurantsSearchActivityBinding>(
    RestaurantsSearchActivityBinding::inflate
) {

    // Fields
    private lateinit var mapFragment: RestaurantsMapFragment
    private lateinit var listFragment: RestaurantsListFragment
    override val viewModel: RestaurantsSearchViewModel by inject()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var rxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(this)
    }

    // Functions
    override fun initUi() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as RestaurantsMapFragment
        listFragment = supportFragmentManager.findFragmentById(R.id.list_fragment) as RestaurantsListFragment
        binding.listMapToggle.throttleTaps { viewModel.listMapToggleTapped() }
        binding.searchEditText.textChanges().subscribe { viewModel.searchTextChanged(it.toString()) }

    }

    override fun subscribeToViewModelObservables() {
        viewModel.apply {
            listOf(
                currentLocationRequested.subscribeToViewModelObservable { getCurrentLocation() },
                searchViewTypeChanged.subscribeToViewModelObservable { updateVisibleFragment(it) },
                searchResultsChanged.subscribeToViewModelObservable {
                    mapFragment.restaurantsUpdated(it)
                    listFragment.restaurantsUpdated(it)
                },
                loadingSpinnerVisibilityChanged.subscribeToViewModelObservable {
                    binding.loadingSpinner.setVisibility(it)
                }
            ).autoDispose()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
            .subscribe { granted ->
                if (!granted) {
                    toast(R.string.failed_to_get_location_permission.resourceString())
                } else {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        viewModel.locationUpdated(location)
                        location?.let {
                            mapFragment.locationUpdated(LatLng(it.latitude, it.longitude))
                        }
                    }
                }
            }
    }

    private fun updateVisibleFragment(viewType: RestaurantsSearchViewModel.ViewType) {
        when (viewType) {
            RestaurantsSearchViewModel.ViewType.List -> {
                binding.apply {
                    listMapToggle.text = R.string.map.resourceString()
                    listMapToggle.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_map_pin.resourceDrawable(),
                        null,
                        null,
                        null
                    )
                    listFragmentContainer.visibility = View.VISIBLE
                    mapFragmentContainer.visibility = View.GONE
                }

            }
            RestaurantsSearchViewModel.ViewType.Map -> {
                binding.apply {
                    listMapToggle.text = R.string.list.resourceString()
                    listMapToggle.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_list.resourceDrawable(),
                        null,
                        null,
                        null
                    )
                    listFragmentContainer.visibility = View.GONE
                    mapFragmentContainer.visibility = View.VISIBLE
                }
            }
        }
    }
}