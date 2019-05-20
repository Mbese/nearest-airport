package com.example.airportapp.models

data class Departure (val iataCode: String, val icaoCode: String, val terminal: String, val gate: String, val scheduledTime: String)