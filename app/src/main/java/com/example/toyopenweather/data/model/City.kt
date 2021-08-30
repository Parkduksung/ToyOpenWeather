package com.example.toyopenweather.data.model

class City : ArrayList<CityItem>()

data class CityItem(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String
)

data class Coord(
    val lat: Int,
    val lon: Double
)