package com.example.toyopenweather.api

import com.example.toyopenweather.api.response.WeatherResponse
import retrofit2.Call

interface WeatherApi {
    fun getCurrentWeatherById(id: Int): Call<WeatherResponse>
}