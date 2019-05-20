package com.example.airportapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AirportApiService {
    @GET("nearby?key=321802-3548c7")
    fun getNearByAirports(@Query("lat") lat: String, @Query("lng") lng: String, @Query("distance") distance: String) : Call<List<NearByAirportModel>>

    @GET("timetable?key=321802-3548c7")
    fun getAirportSchedules(@Query("iataCode") iataCode: String, @Query("type") type: String) : Call<List<AirportFlightScheduleModel>>
}
