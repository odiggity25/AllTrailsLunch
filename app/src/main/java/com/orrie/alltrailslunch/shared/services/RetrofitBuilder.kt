package com.orrie.alltrailslunch.shared.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object {
        fun build(): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(Interceptor { chain ->
                    val request: Request = chain.request()
                    val newRequest: Request = request.newBuilder()
                        .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
                        .build()
                    chain.proceed(newRequest)
                })
                .build()

            return Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        }
    }
}