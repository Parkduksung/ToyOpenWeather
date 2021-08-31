package com.example.toyopenweather.data.model

import com.google.gson.annotations.SerializedName

class CityList : ArrayList<CityItem>()

data class CityItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("coord")
    val coord: Coord
)

data class Coord(
    val lat: Double,
    val lon: Double
)