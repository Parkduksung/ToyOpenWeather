package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityItem

interface CityLocalDataSource {
    suspend fun getCityList(): List<CityItem>
}