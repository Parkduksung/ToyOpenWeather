package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.util.Result

interface CityLocalDataSource {
    suspend fun getCityList(): Result<List<CityItem>>
}