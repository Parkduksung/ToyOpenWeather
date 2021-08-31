package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.util.City
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityLocalDataSourceImpl(private val city: City) : CityLocalDataSource {

    override suspend fun getCityList(): Result<List<CityItem>> = withContext(Dispatchers.IO) {
        return@withContext city.getCityList()
    }
}