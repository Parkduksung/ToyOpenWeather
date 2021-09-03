package com.example.toyopenweather.data.model

import com.example.toyopenweather.room.entity.CityEntity

class CityList : ArrayList<CityItem>()

data class CityItem(
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coord
) {
    fun toCityEntity(): CityEntity =
        CityEntity(
            id = id,
            name = name,
            country = country,
            lat = coord.lat,
            lon = coord.lon
        )
}

data class Coord(
    val lat: Double,
    val lon: Double
)