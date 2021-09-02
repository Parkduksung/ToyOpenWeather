package com.example.toyopenweather.api

import com.example.toyopenweather.api.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val WEATHER_BY_ID = "data/2.5/weather"
        const val APP_KEY = "a07cb097858e46d4e01fbb55f39849d6"
    }

    @GET(WEATHER_BY_ID)
    fun getCurrentWeatherById(
        @Query("id") id: Int,
        @Query("appid") appId: String = APP_KEY
    ): Call<WeatherResponse>
}