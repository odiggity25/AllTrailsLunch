package com.orrie.alltrailslunch.di

import com.orrie.alltrailslunch.restaurants.RestaurantsRepository
import com.orrie.alltrailslunch.restaurants.RestaurantsStore
import com.orrie.alltrailslunch.restaurants.search.RestaurantsSearchViewModel
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantListItemViewModel
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantsListAdapter
import com.orrie.alltrailslunch.restaurants.search.list.RestaurantsListViewModel
import com.orrie.alltrailslunch.restaurants.search.map.RestaurantsMapViewModel
import com.orrie.alltrailslunch.restaurants.services.RestaurantMapper
import com.orrie.alltrailslunch.restaurants.services.RestaurantsService
import com.orrie.alltrailslunch.shared.LocalPrefs
import com.orrie.alltrailslunch.shared.services.RetrofitBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { RestaurantListItemViewModel() }
    viewModel { RestaurantsMapViewModel() }
    viewModel { RestaurantsListViewModel() }
    viewModel { RestaurantsSearchViewModel() }
    single { RestaurantsRepository() }
    factory { RestaurantsListAdapter() }
    single { RetrofitBuilder.build() }
    single { RestaurantMapper() }
    single { RestaurantsService() }
    single { LocalPrefs() }
    single { RestaurantsStore() }
}