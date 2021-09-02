package com.example.toyopenweather.data.repo

import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSource
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class WeatherRepositoryImpl :
    WeatherRepository {

    private val weatherRemoteDataSource by inject<WeatherRemoteDataSource>(WeatherRemoteDataSource::class.java)

    override suspend fun getCurrentWeatherById(id: Int): Result<WeatherResponse> =
        withContext(Dispatchers.IO) {
            return@withContext weatherRemoteDataSource.getCurrentWeatherById(id = id)
        }
}