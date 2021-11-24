package com.orrie.alltrailslunch.shared.services

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit

abstract class BaseService<T>(api: Class<T>): KoinComponent {
    private val retrofit: Retrofit by inject()
    protected val service: T = retrofit.create(api)
}