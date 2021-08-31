package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.model.Coord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityLocalDataSourceImpl : CityLocalDataSource {

    override suspend fun getCityList(): List<CityItem> = withContext(Dispatchers.IO) {
        return@withContext try {
            listOf(
                CityItem(
                    id = 707860,
                    name = "UA",
                    country = "UA",
                    coord = Coord(lat = 44.549999, lon = 34.283333)
                )
            )
        } catch (e: Exception) {
            return@withContext throw java.lang.Exception()
        }
    }
}