package com.example.toyopenweather.data.repo

import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.source.local.CityLocalDataSource
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class CityRepositoryImpl : CityRepository {

    private val cityLocalDataSource by inject<CityLocalDataSource>(CityLocalDataSource::class.java)

    override suspend fun getCityList(): Result<CityList> = withContext(Dispatchers.IO) {
        return@withContext cityLocalDataSource.getCityList()
    }

}