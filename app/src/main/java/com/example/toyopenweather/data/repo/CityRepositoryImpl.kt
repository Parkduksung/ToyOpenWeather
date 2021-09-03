package com.example.toyopenweather.data.repo

import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.data.source.local.CityLocalDataSource
import com.example.toyopenweather.room.entity.CityEntity
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class CityRepositoryImpl : CityRepository {

    private val cityLocalDataSource by inject<CityLocalDataSource>(CityLocalDataSource::class.java)

    override suspend fun getAssetCityList(): Result<CityList> = withContext(Dispatchers.IO) {
        return@withContext cityLocalDataSource.getCityList()
    }

    override suspend fun getCityEntity(cityName: String): Result<CityEntity> =
        withContext(Dispatchers.IO) {
            return@withContext cityLocalDataSource.getCityEntityByName(cityName)
        }

    override suspend fun registerCityList(cityList: CityList): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext cityLocalDataSource.registerCityList(cityList)
        }

    override suspend fun isExistCityList(): Boolean = withContext(Dispatchers.IO) {
        return@withContext cityLocalDataSource.isExistCityList()
    }

    override suspend fun getAllCityEntity(): Result<List<CityEntity>> =
        withContext(Dispatchers.IO) {
            return@withContext cityLocalDataSource.getAllCityEntity()
        }
}