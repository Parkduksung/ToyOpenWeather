package com.example.toyopenweather.data.repo

import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.util.Result

interface CityRepository {
    suspend fun getCityList(): Result<List<CityItem>>
}