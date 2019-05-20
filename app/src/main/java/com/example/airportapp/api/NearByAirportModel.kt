package com.example.airportapp.api

data class NearByAirportModel(
    val nameAirport: String, val codeIataAirport: String, val codeIcaoAirport: String,
    val nameTranslations: String, val latitudeAirport: String, val longitudeAirport: String,
    val timezone: String, val GMT: String, val phone: String,
    val nameCountry: String, val codeIso2Country: String, val codeIataCity: String, val distance: Double
)