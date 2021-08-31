package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.model.Coord
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityLocalDataSourceImpl : CityLocalDataSource {

    override suspend fun getCityList(): Result<List<CityItem>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val cityList = listOf(
                CityItem(
                    id = 707860,
                    name = "UA",
                    country = "UA",
                    coord = Coord(lat = 44.549999, lon = 34.283333)
                )
            )
            Result.success(cityList)
        } catch (e: Exception) {
            Result.failure(Throwable())
        }
    }
}