package com.example.toyopenweather.data.source.remote

import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.util.Result

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeatherById(id: Int): Result<WeatherResponse>
}