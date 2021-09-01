package com.example.toyopenweather.data.source.remote

import com.example.toyopenweather.api.WeatherApi
import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRemoteDataSourceImpl(private val weatherApi: WeatherApi) : WeatherRemoteDataSource {
    override suspend fun getCurrentWeatherById(id: Int): Result<WeatherResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.success(weatherApi.getCurrentWeatherById(id = id).execute().body()!!)
            } catch (e: Exception) {
                Result.failure(Throwable())
            }
        }
}