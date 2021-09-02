package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.util.City
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityLocalDataSourceImpl(private val city: City) : CityLocalDataSource {

    override suspend fun getCityList(): Result<CityList> = withContext(Dispatchers.IO) {
        return@withContext city.getCityList()
    }


    companion object {

        private var instance: CityLocalDataSourceImpl? = null

        fun getInstance(
            city: City
        ): CityLocalDataSource =
            instance ?: CityLocalDataSourceImpl(city).also {
                instance = it
            }

    }
}