package com.orrie.alltrailslunch.restaurants.search.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.orrie.alltrailslunch.databinding.RestaurantsMapFragmentBinding
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.shared.views.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Shows restaurants on a map
 */
class RestaurantsMapFragment : BaseFragment<RestaurantsMapFragmentBinding>(
    RestaurantsMapFragmentBinding::inflate
) {
    private lateinit var map: GoogleMap
    override val viewModel: RestaurantsMapViewModel by viewModel()

    override fun initUi() {
        initMap()
    }

    override fun subscribeToViewModelObservables() {
        // TODO
    }

    private fun initMap() {
//        (childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment)
//            .getMapAsync {
//                map = it
//                mapInitialized()
//            }
    }

    private fun mapInitialized() {
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun updateResults(restaurants: List<Restaurant>) {
        // TODO: Show restaurants on map
    }
}