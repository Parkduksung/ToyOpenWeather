package com.example.toyopenweather.data.repo

import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.source.local.CityLocalDataSource
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityRepositoryImpl(private val cityLocalDataSource: CityLocalDataSource) : CityRepository {

    override suspend fun getCityList(): Result<List<CityItem>> = withContext(Dispatchers.IO) {
        return@withContext cityLocalDataSource.getCityList()
    }
}