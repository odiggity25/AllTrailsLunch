package com.orrie.alltrailslunch.di

import com.orrie.alltrailslunch.restaurants.RestaurantsRepository
import com.orrie.alltrailslunch.restaurants.search.RestaurantsSearchViewModel
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantListItemViewModel
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantsListAdapter
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantsListViewModel
import com.orrie.alltrailslunch.restaurants.search.map.RestaurantsMapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { RestaurantListItemViewModel() }
    viewModel { RestaurantsMapViewModel() }
    viewModel { RestaurantsListViewModel() }
    viewModel { RestaurantsSearchViewModel() }
    single { RestaurantsRepository() }
    single { RestaurantsListAdapter() }
}