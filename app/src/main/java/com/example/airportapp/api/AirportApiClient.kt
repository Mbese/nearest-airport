package com.example.airportapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AirportApiClient {

    companion object {

        val base_url = "http://aviation-edge.com/api/public/"

        fun getRetrofitInstance(): Retrofit.Builder {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base_url)
        }

        fun getService(): AirportApiService {
            return getRetrofitInstance().build().create(AirportApiService::class.java)
        }
    }
}