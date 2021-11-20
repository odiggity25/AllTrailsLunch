package com.orrie.alltrailslunch.restaurants.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.orrie.alltrailslunch.R
import com.orrie.alltrailslunch.databinding.MainActivityBinding
import com.orrie.alltrailslunch.shared.resourceString
import com.orrie.alltrailslunch.shared.views.BaseActivity
import com.orrie.alltrailslunch.shared.views.subscribeToViewModelObservable
import com.orrie.alltrailslunch.shared.views.throttleTaps
import org.koin.android.ext.android.inject

/**
 * This is the main activity that houses the search bar along with the map fragment and
 * list fragment for the search results
 */
class MainActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: MainActivityBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var listFragment: Fragment
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        subscribeToViewModelObservables()
    }

    override fun initUi() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        listFragment = supportFragmentManager.findFragmentById(R.id.list_fragment) as Fragment

        binding.listMapToggle.throttleTaps { viewModel.listMapToggleTapped() }
    }

    override fun subscribeToViewModelObservables() {
        viewModel.apply {
            viewStateChanged.subscribeToViewModelObservable { updateVisibleFragment(it) }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun updateVisibleFragment(state: MainViewModel.ViewState) {
        when (state) {
            MainViewModel.ViewState.List -> {
                binding.listMapToggle.text = R.string.map.resourceString()
                binding.listFragmentContainer.visibility = View.VISIBLE
                binding.mapFragmentContainer.visibility = View.GONE
            }
            MainViewModel.ViewState.Map -> {
                binding.listMapToggle.text = R.string.list.resourceString()
                binding.listFragmentContainer.visibility = View.GONE
                binding.mapFragmentContainer.visibility = View.VISIBLE
            }
        }
    }
}