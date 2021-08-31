package com.example.toyopenweather.data.model

class CityList : ArrayList<CityItem>()

data class CityItem(
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coord
)

data class Coord(
    val lat: Double,
    val lon: Double
)