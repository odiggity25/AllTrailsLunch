package com.orrie.alltrailslunch.di

import com.orrie.alltrailslunch.restaurants.RestaurantsRepository
import com.orrie.alltrailslunch.restaurants.search.MainViewModel
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantListItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainViewModel() }

    // Restaurants
    viewModel { RestaurantListItemViewModel() }
    single { RestaurantsRepository() }
}