package com.example.airportapp.api

import com.example.airportapp.models.*

data class AirportFlightScheduleModel(
    val type: String, val status: String, val departure: Departure,
    val arrival: Arrival, val airline: Airline, val flight: Flight,
    val codeShared: CodeShared
)
