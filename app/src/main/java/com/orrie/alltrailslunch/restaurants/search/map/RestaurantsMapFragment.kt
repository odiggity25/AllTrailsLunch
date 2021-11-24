package com.orrie.alltrailslunch.restaurants.search.map

import android.annotation.SuppressLint
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.orrie.alltrailslunch.R
import com.orrie.alltrailslunch.databinding.RestaurantsMapFragmentBinding
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantListItemView
import com.orrie.alltrailslunch.shared.views.BaseFragment
import com.orrie.alltrailslunch.shared.views.subscribeToViewModelObservable
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Shows restaurants on a map
 */
class RestaurantsMapFragment : BaseFragment<RestaurantsMapFragmentBinding>(
    RestaurantsMapFragmentBinding::inflate
) {
    // Properties
    private lateinit var map: GoogleMap
    override val viewModel: RestaurantsMapViewModel by viewModel()

    // Functions
    override fun initUi() {
        initMap()
    }

    override fun subscribeToViewModelObservables() {
        // No-op (this rarely happens in real apps)
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun initMap() {
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
            .getMapAsync {
                map = it
                map.setInfoWindowAdapter(RestaurantInfoWindowAdapter())
                subscribeToMarkerUpdates()
            }
    }

    /**
     * Wait until after map is initialized to subscribe to marker updates so that
     * we can update the map with the markers when they arrive
     */
    private fun subscribeToMarkerUpdates() {
        viewModel.markersUpdated.subscribeToViewModelObservable { markerOptions ->
            map.clear()
            markerOptions.forEach { markerOptionsWithRestaurant ->
                map.addMarker(markerOptionsWithRestaurant.markerOptions)?.let {
                    it.tag =  markerOptionsWithRestaurant.restaurant
                }
            }
        }
    }

    fun locationUpdated(latLng: LatLng) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
    }

    fun restaurantsUpdated(restaurants: List<Restaurant>) {
        viewModel.restaurantsUpdated(restaurants)
    }

    inner class RestaurantInfoWindowAdapter: GoogleMap.InfoWindowAdapter {
        override fun getInfoContents(marker: Marker): View? {
            return null
        }

        override fun getInfoWindow(marker: Marker): View? {
            val context = context ?: return null
            return RestaurantListItemView(context, forMapInfoWindow = true).apply {
                bind((marker.tag as Restaurant))
            }
        }
    }
}