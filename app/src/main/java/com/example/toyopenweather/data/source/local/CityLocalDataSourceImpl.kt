package com.example.toyopenweather.data.source.local

import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.util.City
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class CityLocalDataSourceImpl : CityLocalDataSource {

    private val city by inject<City>(City::class.java)


    override suspend fun getCityList(): Result<CityList> = withContext(Dispatchers.IO) {
        return@withContext city.getCityList()
    }

}