package com.example.toyopenweather.data.repo

import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSource
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(private val weatherRemoteDataSource: WeatherRemoteDataSource) :
    WeatherRepository {

    override suspend fun getCurrentWeatherById(id: Int): Result<WeatherResponse> =
        withContext(Dispatchers.IO) {
            return@withContext weatherRemoteDataSource.getCurrentWeatherById(id = id)
        }
}