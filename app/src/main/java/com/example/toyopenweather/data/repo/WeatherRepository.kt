package com.example.toyopenweather.data.repo

import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.util.Result

interface WeatherRepository {
    suspend fun getCurrentWeatherById(id: Int): Result<WeatherResponse>
}